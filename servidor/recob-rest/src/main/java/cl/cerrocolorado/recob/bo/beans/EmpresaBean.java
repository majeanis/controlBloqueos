package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.bo.EmpresaBO;
import cl.cerrocolorado.recob.po.EmpresaPO;
import cl.cerrocolorado.recob.to.entidades.EmpresaTO;
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

    @Override
    @Transaccional
    public Respuesta<EmpresaTO> save(EmpresaTO empresa)
    {
        logger.info ("save[INI] empresa: {}", empresa);
        
        Resultado rtdo = new ResultadoProceso();
        if(empresa == null)
        {
        	rtdo.addError(this.getClass(), "Debe informar los datos de la empresa" );
        	logger.info("save[FIN] no se informaron los datos de la empresa");
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

        logger.debug( "save[001] es un nuevo registro?: {}", empresa.isIdBlank() );
        EmpresaTO otra = empresaPO.get(empresa);
        if(empresa.isIdBlank())
        {
            if(otra!=null)
            {
                rtdo.addError(this.getClass(), "Ya existe Empresa con R.U.T. %s", empresa.getRut().toText());
            }
        } else if ( otra == null )
        {
            rtdo.addError(this.getClass(), "No existe Empresa con R.U.T. %s", empresa.getRut().toText());
        }

        logger.debug("save[002] validaciones exitosas?: {}", rtdo.esExitoso() );
        if( !rtdo.esExitoso() )
        {
            logger.info ("save[FIN] saliendo del método por errores de validación: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        // Si llegamos a este punto la Caja puede ser Guardada
        empresaPO.save(empresa);
        rtdo.addMensaje(this.getClass(), "Empresa guardada con éxito");
        
        logger.info ("save[FIN] registro guardado con exito: {}", empresa );
        return Respuesta.of(rtdo, empresa);
    }

    @Transaccional
    @Override
    public Respuesta<EmpresaTO> delete(EmpresaTO pkEmpresa) throws Exception
    {
        logger.info ("delete[INI] pkEmpresa: {}", pkEmpresa );
        Resultado rtdo = new ResultadoProceso();
        
        if(pkEmpresa == null || pkEmpresa.isKeyBlank())
        {
        	rtdo.addError(this.getClass(), "Debe informar el R.U.T. de la empresa" );
        	logger.info("delete[FIN] no se eliminó por fallas en validación", rtdo);
        	return Respuesta.of(rtdo);
        }
        
        EmpresaTO empresa = empresaPO.get(pkEmpresa);
        if( empresa == null )
        {
            rtdo.addError(EmpresaBean.class, "No existe Empresa con RUT: %s", pkEmpresa.getRut().toText() );
            logger.info ("delete[FIN] no existe el registro: {}", pkEmpresa );
            return Respuesta.of(rtdo);
        }

        if(!empresaPO.isDeleteable(empresa))
        {
            rtdo.addError(this.getClass(), "Empresa tiene registros asociados" );
            logger.info ("delete[FIN] registro no puede ser eliminado");
            return Respuesta.of(rtdo);
        }

        empresaPO.delete(empresa);
        logger.debug("delete[001] despues de eliminar el registro: {}", empresa );
        
        rtdo.addMensaje(EmpresaBean.class, "Empresa RUT: %s eliminada con éxito", pkEmpresa.getRut().toText() );
        logger.info ("delete[FIN] caja eliminada con exito: {} {}", rtdo, empresa );
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

        EmpresaTO empresa = empresaPO.get(pkEmpresa);
        
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
