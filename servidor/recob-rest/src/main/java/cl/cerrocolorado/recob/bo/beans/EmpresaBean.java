package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.bo.EmpresaBO;
import cl.cerrocolorado.recob.po.EmpresaPO;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Rut;
import java.util.List;
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

    @Transaccional
    @Override
    public Respuesta<EmpresaTO> guardar(EmpresaTO empresa) throws Exception
    {
        logger.info ("guardar[INI] empresa: {}", empresa);
        
        Resultado rtdo = new ResultadoProceso();

        if(empresa == null)
        {
        	rtdo.addError(this.getClass(), "Debe informar los datos de la empresa" );
        	logger.info("guardar[FIN] no se informaron los datos de la empresa");
        	return new Respuesta<>(rtdo);
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

        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] saliendo del método por errores de validación: {}", rtdo );
            return new Respuesta<>(rtdo);
        }

        // Buscamos si ya existe un registro para el RUT dado, en cuyo caso actualizamos
        EmpresaTO otra = empresaPO.get(empresa);
        if(otra != null)
        {
            logger.debug("guardar[001] registro de empresaa ya existe: {}", otra);
            empresa.setId(otra.getId());
        }

        // Si llegamos a este punto la Caja puede ser Guardada
        empresaPO.guardar(empresa);
        logger.info ("guardar[FIN] registro guardado con exito: {}", empresa );
        return new Respuesta<>(empresa);
    }
    
    @Transaccional
    @Override
    public Resultado eliminar(EmpresaTO pkEmpresa) throws Exception
    {
        logger.info ("eliminar[INI] pkEmpresa: {}", pkEmpresa );
        Resultado rtdo = new ResultadoProceso();
        
        if(pkEmpresa == null)
        {
        	rtdo.addError(this.getClass(), "Debe informar la empresa que se desea eliminar" );
        } else if( Rut.isBlank(pkEmpresa.getRut()) )
        {
            rtdo.addError(EmpresaBean.class, "Debe informar el RUT de la empresa" );
        }
        
        if(!rtdo.esExitoso())
        {
        	logger.info("eliminar[FIN] no se eliminó por fallas en validación", rtdo);
        	return rtdo;
        }

        EmpresaTO otra = empresaPO.get(pkEmpresa);
        if( otra == null )
        {
            rtdo.addError(EmpresaBean.class, "No existe Empresa con RUT: #{1}", String.valueOf( pkEmpresa.getRut() ) );
            logger.info ("eliminar[FIN] no existe el registro: {}", pkEmpresa );
            return rtdo;
        }
        
        empresaPO.eliminar(otra);
        logger.debug("eliminar[001] despues de eliminar el registro: {}", otra );
        
        rtdo.addMensaje(EmpresaBean.class, "Empresa RUT: #{1} eliminada con éxito", pkEmpresa.getRut().toText() );
        logger.info ("eliminar[FIN] caja eliminada con exito: {} {}", rtdo, otra );
        return rtdo;
    }
    
    @Override
    public EmpresaTO get(EmpresaTO pkEmpresa)
    {
        logger.info ("get[INI] pkEmpresa: {}", pkEmpresa );

        EmpresaTO empresa = empresaPO.get(pkEmpresa);
        
        logger.info ("get[FIN] resultado busqueda: {}", empresa );
        return empresa;
    }

    @Override
    public List<EmpresaTO> getVigentes()
    {
        logger.info ("getVigentes[INI]" );

        List<EmpresaTO> empresas = empresaPO.getList(true);
        
        logger.info ("getVigentes[FIN] cantidad registros encontrados: {}", empresas.size() );
        return empresas;
    }

    @Override
    public List<EmpresaTO> getTodos()
    {
        logger.info ("getTodos[INI]" );

        List<EmpresaTO> empresas = empresaPO.getList((Boolean) null);
        
        logger.info ("getTodos[FIN] cantidad registros encontrados: {}", empresas.size() );
        return empresas;
    }
}
