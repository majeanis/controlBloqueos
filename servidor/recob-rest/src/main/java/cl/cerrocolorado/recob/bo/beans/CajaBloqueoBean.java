package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.bo.CajaBloqueoBO;
import cl.cerrocolorado.recob.po.CajaBloqueoPO;
import cl.cerrocolorado.recob.po.UbicacionPO;
import cl.cerrocolorado.recob.to.entidades.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.UbicacionTO;
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

    private Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO caja, boolean esNuevo)
    {
        logger.info ("guardar[INI] caja: {}", caja);
        logger.info ("guardar[INI] esNuevo: {}", esNuevo);
        
        Resultado rtdo = new ResultadoProceso();

        if( caja == null )
        {
        	rtdo.addError( this.getClass(), "Debe informar los datos de la Caja");
            logger.info ("guardar[FIN] caja informada en null" );        	
            return Respuesta.of(rtdo);
        } 
        if ( caja.getVigente() == null )
        {
            rtdo.addError( this.getClass(), "Debe informar Vigencia de la Caja");
        }
        if( Utils.nvl(caja.getNumero(), 0) == 0 )
        {
            rtdo.addError( this.getClass(), "Debe informar N° de la Caja" );
        }
        if( caja.getNombre() == null )
        {
            rtdo.addError( this.getClass(), "Debe informar nombre de la Caja" );
        }
        if( caja.getUbicacion() == null || caja.getUbicacion().isKeyBlank())
        {
            rtdo.addError( this.getClass(), "Caja debe estar asociada a una Ubicación" );
        } else
        {
		    UbicacionTO ubicacion = ubicacionPO.get(caja.getUbicacion());
		    if( ubicacion == null )
		    {
		        rtdo.addError( this.getClass(), "La ubicación informada no existe [id: %d]", caja.getUbicacion().getId());
		    }
        }

        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] saliendo por errores de validación: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        CajaBloqueoTO otra = cajaPO.obtener( caja );
        if( esNuevo )
        {
            if(otra != null)
            {
                rtdo.addError(this.getClass(), "Ya existe Caja con el N° %d", caja.getNumero());
            }
        } else if( otra == null )
        {
            rtdo.addError(this.getClass(), "No existe Caja con el N° %d", caja.getNumero());
        } else
        {
            caja.setId(otra.getId());
        }

        logger.debug ("guardar[001] antes de revisar el resultado de las validaciones: {}", rtdo);
        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] saliendo por errores de validación: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        cajaPO.guardar(caja);
        rtdo.addMensaje(this.getClass(), "Caja de Bloqueo guardada con éxito");
        
        logger.info ("guardar[FIN] caja guardada con exito: {}", caja );
        return Respuesta.of(rtdo, caja);
    }

    @Transaccional
    @Override
    public Respuesta<CajaBloqueoTO> crear(CajaBloqueoTO caja) throws Exception
    {
        return guardar(caja,true);
    }
    
    @Transaccional
    @Override
    public Respuesta<CajaBloqueoTO> modificar(CajaBloqueoTO caja) throws Exception
    {
        return guardar(caja,false);
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
            rtdo.addError(this.getClass(), "No existe Caja N° %d", pkCaja.getNumero() );
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
        
        rtdo.addMensaje(this.getClass(), "Caja N° %d eliminada con éxito", caja.getNumero());
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
            rtdo.addMensaje(this.getClass(), "No se encontró registro para Caja N° %d", pkCaja.getNumero());
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
