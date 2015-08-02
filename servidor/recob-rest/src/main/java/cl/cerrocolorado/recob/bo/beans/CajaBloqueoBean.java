package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.bo.CajaBloqueoBO;
import cl.cerrocolorado.recob.po.CajaBloqueoPO;
import cl.cerrocolorado.recob.po.UbicacionPO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.mensajes.RegistrosQueryInfo;
import java.util.List;
import java.util.Optional;
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
@Service("cajaBloqueoBO")
public class CajaBloqueoBean implements CajaBloqueoBO
{
    private static final Logger logger = LogManager.getLogger(CajaBloqueoBean.class);
    
    @Autowired
    private CajaBloqueoPO cajaPO;

    @Autowired
    private UbicacionPO ubicacionPO;

    @Transaccional
    @Override
    public Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO cajaBloqueo) throws Exception
    {
        logger.info ("guardar[INI] cajaBloqueo: {}", cajaBloqueo);
        
        Resultado rtdo = new ResultadoProceso();

        if( cajaBloqueo == null )
        {
        	rtdo.addError( this.getClass(), "Debe informar los datos de la Caja");
            logger.info ("guardar[FIN] caja informada en null" );        	
            return Respuesta.of(rtdo);
        } 

        if ( cajaBloqueo.getVigente() == null )
        {
            rtdo.addError( this.getClass(), "Debe informar Vigencia de la Caja");
        }
        
        if( cajaBloqueo.getNumero() == null )
        {
            rtdo.addError( this.getClass(), "Debe informar N° de la Caja" );
        } else if( cajaBloqueo.getNumero() == 0 )
        {
            rtdo.addError( this.getClass(), "El N° de Caja es inválido" );
        }
        
        if( cajaBloqueo.getNombre() == null )
        {
            rtdo.addError( this.getClass(), "Debe informar nombre de la Caja" );
        }
        
        if( cajaBloqueo.getUbicacion() == null )
        {
            rtdo.addError( this.getClass(), "Caja debe estar asociada a una Ubicación" );
        } else if (cajaBloqueo.getUbicacion().getId() == null )
        {
            rtdo.addError( this.getClass(), "Caja debe estar asociada a una Ubicación" );
        } else
        {
		    UbicacionTO ubicacion = ubicacionPO.get(cajaBloqueo.getUbicacion());
		    if( ubicacion == null )
		    {
		        rtdo.addError( this.getClass(), "La ubicación informada no existe [id: #{1}]", String.valueOf(cajaBloqueo.getUbicacion().getId()));
		    }
        }

        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] saliendo del método por campos en NULL: {}", rtdo );
            return Respuesta.of(rtdo);
        }
        
        // Buscamos la preexistencia de la caja
        CajaBloqueoTO otraCaja = cajaPO.get( cajaBloqueo );
        if( otraCaja != null ) 
        {
            logger.debug("guardar[001] ya existe registro para la caja: {}", otraCaja );
            cajaBloqueo.setId(otraCaja.getId());
        }

        // Si llegamos a este punto la Caja puede ser Guardada
        cajaPO.guardar(cajaBloqueo);
        rtdo.addMensaje(this.getClass(), "Caja de Bloqueo guardada con éxito");
        
        logger.info ("guardar[FIN] caja guardada con exito: {}", cajaBloqueo );
        return Respuesta.of(rtdo, cajaBloqueo);
    }
    
    @Transaccional
    @Override
    public Respuesta<CajaBloqueoTO> eliminar(CajaBloqueoTO pkCaja) throws Exception
    {
        logger.info ("eliminar[INI] caja: {}", pkCaja );
        Resultado rtdo = new ResultadoProceso();
        
        if( pkCaja == null || pkCaja.isKeyBlank() )
        {
        	rtdo.addError(this.getClass(), "Debe informar la identificación de la caja" );
        	logger.info( "eliminar[FIN] no se informaron todos los filtros: {}", pkCaja );
        	return Respuesta.of(rtdo);
        }
        
        CajaBloqueoTO caja = cajaPO.get(pkCaja);
        if( caja == null )
        {
            rtdo.addError(this.getClass(), "No existe Caja N° #{1}", String.valueOf( pkCaja.getNumero() ) );
            logger.info ("eliminar[FIN] no existe caja: {}", pkCaja );
            return Respuesta.of(rtdo);
        }
        
        if(!cajaPO.esEliminable(caja))
        {
            rtdo.addError(this.getClass(), "Caja de Bloqueo tiene registros asociados" );
            logger.info ("eliminar[FIN] registro no puede ser eliminado");
            return Respuesta.of(rtdo);
        }

        cajaPO.eliminar(caja);
        logger.debug("eliminar[001] despues de eliminar la caja: {}", caja );
        
        rtdo.addMensaje(this.getClass(), "Caja N° #{1} eliminada con éxito", String.valueOf( caja.getNumero() ) );
        logger.info ("eliminar[FIN] caja eliminada con exito: {} {}", rtdo, caja );
        return Respuesta.of(rtdo,caja);
    }
    
    @Override
    public Respuesta<CajaBloqueoTO> get(CajaBloqueoTO pkCaja)
    {
        logger.info ("get[INI] caja: {}", pkCaja );

        Resultado rtdo = new ResultadoProceso();
        if(pkCaja == null || pkCaja.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación de la caja" );
            logger.info("get[FIN] No se informaron los datos mínimos: {}", pkCaja );
            return Respuesta.of(rtdo);
        }

        CajaBloqueoTO caja = cajaPO.get(pkCaja);
        if( caja == null )
        {
            rtdo.addMensaje(this.getClass(), "No se encontró registro para Caja N° #{1}", String.valueOf(pkCaja.getNumero()));
        }

        logger.info ("get[FIN] resultado búsqueda: {}", caja );
        return Respuesta.of(rtdo, caja);
    }

    @Override
    public Respuesta<List<CajaBloqueoTO>> getTodos(UbicacionTO pkUbicacion, 
                                                   Optional<Boolean> vigencia)
    {
        logger.info ("getTodos[INI] ubicacion: {}", pkUbicacion );
        logger.info ("getTodos[INI] vigencia: {}", vigencia);

        Resultado rtdo = new ResultadoProceso();
        if(pkUbicacion == null || pkUbicacion.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la ubicación");
            logger.info("getTodos[FIN] pkUbicacion llegó en NULL");
            return Respuesta.of(rtdo);
        }

        List<CajaBloqueoTO> cajas = cajaPO.getList(pkUbicacion, vigencia);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), cajas.size()));

        logger.info ("getTodos[FIN] cantidad registros encontrados: {}", cajas.size() );
        return Respuesta.of(rtdo, cajas);
    }
    
    @Override
    public Respuesta<List<CajaBloqueoTO>> getVigentes(UbicacionTO pkUbicacion)
    {
        return getTodos(pkUbicacion, Optional.of(Boolean.TRUE));
    }
}
