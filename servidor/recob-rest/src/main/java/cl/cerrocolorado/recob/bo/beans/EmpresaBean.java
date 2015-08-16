package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.bo.EmpresaBO;
import cl.cerrocolorado.recob.po.EmpresaPO;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Rut;
import cl.cerrocolorado.recob.utils.mensajes.RegistrosQueryInfo;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author mauricio.camara
 */
@Scope("singleton")
@Service("empresaBO")
public class EmpresaBean implements EmpresaBO
{
    private static final Logger logger = LogManager.getLogger(EmpresaBean.class);
    
    @Autowired
    private EmpresaPO empresaPO;

    private Respuesta<EmpresaTO> guardar(EmpresaTO empresa, boolean esNuevo) throws Exception
    {
        logger.info ("guardar[INI] empresa: {}", empresa);
        logger.info ("guardar[INI] esNuevo: {}", esNuevo);
        
        Resultado rtdo = new ResultadoProceso();

        if(empresa == null)
        {
        	rtdo.addError(this.getClass(), "Debe informar los datos de la empresa" );
        	logger.info("guardar[FIN] no se informaron los datos de la empresa");
        	return Respuesta.of(rtdo);
        }
        if ( empresa.getVigente() == null )
        {
            rtdo.addError(EmpresaBean.class, "Debe informar vigencia de la empresa");
        }
        if( StringUtils.isBlank(empresa.getNombre()) )
        {
            rtdo.addError(EmpresaBean.class, "Debe informar nombre de la empresa" );
        }
        if( Rut.isBlank(empresa.getRut()) )
        {
            rtdo.addError(EmpresaBean.class, "Debe informar RUT de la empresa" );
        }

        EmpresaTO otra = empresaPO.obtener(empresa);
        if(esNuevo)
        {
            if(otra!=null)
            {
                rtdo.addError(this.getClass(), "Ya existe Empresa con R.U.T. #{1}", empresa.getRut().toText());
            }
        } else if ( otra == null )
        {
            rtdo.addError(this.getClass(), "No existe Empresa con R.U.T. #{1}", empresa.getRut().toText());
        } else
        {
            empresa.setId(otra.getId());
        }

        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] saliendo del método por errores de validación: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        // Si llegamos a este punto la Caja puede ser Guardada
        empresaPO.guardar(empresa);
        rtdo.addMensaje(this.getClass(), "Empresa guardada con éxito");
        
        logger.info ("guardar[FIN] registro guardado con exito: {}", empresa );
        return Respuesta.of(rtdo, empresa);
    }
        
    @Transaccional
    @Override
    public Respuesta<EmpresaTO> crear(EmpresaTO empresa) throws Exception
    {
        return guardar(empresa, true);
    }

    @Transaccional
    @Override
    public Respuesta<EmpresaTO> modificar(EmpresaTO empresa) throws Exception
    {
        return guardar(empresa, false);
    }

    @Transaccional
    @Override
    public Respuesta<EmpresaTO> eliminar(EmpresaTO pkEmpresa) throws Exception
    {
        logger.info ("eliminar[INI] pkEmpresa: {}", pkEmpresa );
        Resultado rtdo = new ResultadoProceso();
        
        if(pkEmpresa == null || pkEmpresa.isKeyBlank())
        {
        	rtdo.addError(this.getClass(), "Debe informar el R.U.T. de la empresa" );
        	logger.info("eliminar[FIN] no se eliminó por fallas en validación", rtdo);
        	return Respuesta.of(rtdo);
        }
        
        EmpresaTO empresa = empresaPO.obtener(pkEmpresa);
        if( empresa == null )
        {
            rtdo.addError(EmpresaBean.class, "No existe Empresa con RUT: #{1}", String.valueOf( pkEmpresa.getRut() ) );
            logger.info ("eliminar[FIN] no existe el registro: {}", pkEmpresa );
            return Respuesta.of(rtdo);
        }

        if(!empresaPO.esEliminable(empresa))
        {
            rtdo.addError(this.getClass(), "Empresa tiene registros asociados" );
            logger.info ("eliminar[FIN] registro no puede ser eliminado");
            return Respuesta.of(rtdo);
        }

        empresaPO.eliminar(empresa);
        logger.debug("eliminar[001] despues de eliminar el registro: {}", empresa );
        
        rtdo.addMensaje(EmpresaBean.class, "Empresa RUT: #{1} eliminada con éxito", pkEmpresa.getRut().toText() );
        logger.info ("eliminar[FIN] caja eliminada con exito: {} {}", rtdo, empresa );
        return Respuesta.of(rtdo,empresa);
    }
    
    @Override
    public Respuesta<EmpresaTO> get(EmpresaTO pkEmpresa)
    {
        logger.info ("get[INI] pkEmpresa: {}", pkEmpresa );

        Resultado rtdo = new ResultadoProceso();
        
        if(pkEmpresa == null || pkEmpresa.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar el R.U.T. de la empresa");
            return Respuesta.of(rtdo);
        }

        EmpresaTO empresa = empresaPO.obtener(pkEmpresa);
        
        logger.info ("get[FIN] resultado busqueda: {}", empresa );
        return Respuesta.of(empresa);
    }

    @Override
    public Respuesta<List<EmpresaTO>> getEmpresas(Optional<Boolean> vigencia)
    {
        logger.info ("getEmpresas[INI] vigencia: {}", vigencia );

        Resultado rtdo = new ResultadoProceso();
        List<EmpresaTO> lista = empresaPO.getList(vigencia);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));
        
        logger.info ("getEmpresas[FIN] cantidad registros encontrados: {}", lista.size() );
        return Respuesta.of(rtdo,lista);
    }
}
