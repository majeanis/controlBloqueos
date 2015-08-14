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
import cl.cerrocolorado.recob.utils.Utils;
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

    private Resultado validarCaja(CajaBloqueoTO cajaBloqueo)
    {
        logger.info ("validarCaja[INI] cajaBloqueo: {}", cajaBloqueo);
        Resultado rtdo = new ResultadoProceso();

        if( cajaBloqueo == null )
        {
        	rtdo.addError( this.getClass(), "Debe informar los datos de la Caja");
            logger.info ("validarCaja[FIN] caja informada en null" );        	
            return rtdo;
        } 
        if ( cajaBloqueo.getVigente() == null )
        {
            rtdo.addError( this.getClass(), "Debe informar Vigencia de la Caja");
        }
        if( Utils.nvl(cajaBloqueo.getNumero(), 0) == 0 )
        {
            rtdo.addError( this.getClass(), "Debe informar N° de la Caja" );
        }
        if( cajaBloqueo.getNombre() == null )
        {
            rtdo.addError( this.getClass(), "Debe informar nombre de la Caja" );
        }
        if( cajaBloqueo.getUbicacion() == null || cajaBloqueo.getUbicacion().isKeyBlank())
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

        logger.info ("validarCaja[FIN] resultado de la validación: {}", rtdo);
        return rtdo;
    }

    @Transaccional
    @Override
    public Respuesta<CajaBloqueoTO> crear(CajaBloqueoTO caja) throws Exception
    {
        logger.info ("crear[INI] caja: {}", caja);
        
        Resultado rtdo = validarCaja( caja );
        CajaBloqueoTO otra = cajaPO.obtener( caja );
        if(otra!=null)
        {
            rtdo.addError(this.getClass(), "Ya existe Caja con el N° #{1}", String.valueOf(caja.getNumero()) );
        }
        if( !rtdo.esExitoso() )
        {
            logger.info ("crear[FIN] saliendo por errores de validación: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        // Si llegamos a este punto la Caja puede ser Guardada
        cajaPO.crear(caja);
        rtdo.addMensaje(this.getClass(), "Caja de Bloqueo creada con éxito");
        
        logger.info ("crear[FIN] caja guardada con exito: {}", caja );
        return Respuesta.of(rtdo, caja);
    }
    
    @Transaccional
    @Override
    public Respuesta<CajaBloqueoTO> modificar(CajaBloqueoTO caja) throws Exception
    {
        logger.info ("modificar[INI] caja: {}", caja);
        
        Resultado rtdo = validarCaja( caja );
        CajaBloqueoTO otra = cajaPO.obtener( caja );
        if(otra==null)
        {
            rtdo.addError(this.getClass(), "No existe Caja con el N° #{1}", String.valueOf(caja.getNumero()) );
        }
        if( !rtdo.esExitoso() )
        {
            logger.info ("modificar[FIN] saliendo por errores de validación: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        // Si llegamos a este punto la Caja puede ser Guardada
        cajaPO.modificar(caja);
        rtdo.addMensaje(this.getClass(), "Caja de Bloqueo modificada con éxito");
        
        logger.info ("modificar[FIN] caja guardada con exito: {}", caja );
        return Respuesta.of(rtdo, caja);
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
        
        CajaBloqueoTO caja = cajaPO.obtener(pkCaja);
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

        CajaBloqueoTO caja = cajaPO.obtener(pkCaja);
        if( caja == null )
        {
            rtdo.addMensaje(this.getClass(), "No se encontró registro para Caja N° #{1}", String.valueOf(pkCaja.getNumero()));
        }

        logger.info ("get[FIN] resultado búsqueda: {}", caja );
        return Respuesta.of(rtdo, caja);
    }

    @Override
    public Respuesta<List<CajaBloqueoTO>> getCajas(UbicacionTO pkUbicacion, 
                                                   Optional<Boolean> vigencia)
    {
        logger.info ("getCajas[INI] ubicacion: {}", pkUbicacion );
        logger.info ("getCajas[INI] vigencia: {}", vigencia);

        Resultado rtdo = new ResultadoProceso();
        if(pkUbicacion == null || pkUbicacion.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la ubicación");
            logger.info("getCajas[FIN] pkUbicacion llegó en NULL");
            return Respuesta.of(rtdo);
        }

        List<CajaBloqueoTO> cajas = cajaPO.getList(pkUbicacion, vigencia);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), cajas.size()));

        logger.info ("getCajas[FIN] cantidad registros encontrados: {}", cajas.size() );
        return Respuesta.of(rtdo, cajas);
    }
}
