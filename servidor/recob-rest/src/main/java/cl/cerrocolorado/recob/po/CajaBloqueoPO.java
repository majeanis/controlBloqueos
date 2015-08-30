package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.entidades.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.UbicacionTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mauricio.camara
 */
@Scope("singleton")
@Repository
public class CajaBloqueoPO implements BasePO<CajaBloqueoTO>
{
    private static final Logger logger = LogManager.getLogger( CajaBloqueoPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    @Override
    public CajaBloqueoTO guardar(CajaBloqueoTO caja)
    {
        logger.info ("guardar[INI] caja: {}", caja);

        if( caja.isIdBlank() )
        {
            mapper.insertCajaBloqueo( caja );    
            logger.debug("guardar[001] después de insertar la caja: {}", caja);
        } else
        {
            mapper.updateCajaBloqueo( caja );
            logger.debug("guardar[002] después de actualizar la caja: {}", caja);
        }
        
        logger.info ("guardar[FIN] caja guardada con éxito: {}", caja);
        return caja;
    }

    @Override
    public void eliminar(CajaBloqueoTO pkCaja)
    {
        logger.info ("delete[INI] pkCaja: {}", pkCaja );
        mapper.deleteCajaBloqueo(pkCaja);
        logger.info ("delete[FIN] despues de eliminar a la caja: {}", pkCaja );
    }

    @Override
    public CajaBloqueoTO obtener(CajaBloqueoTO pkCaja)
    {
        logger.info ("get[INI] pkCaja: {}", pkCaja );
        
        Map<String, Object> params = new HashMap<>();
        params.put( "ubicacion", pkCaja.getUbicacion() );
        params.put( "caja", pkCaja );
        logger.debug("get[001] parametros: {}", params);
        
        List<CajaBloqueoTO> cajas = mapper.selectCajasBloqueos( params );
        logger.debug("get[002] despues de ejecutar el select: {}", cajas.size() );
        
        if(cajas.isEmpty())
        {
            logger.info ("get[FIN] no se encontró registro de la caja: {}", params );
            return null;
        }
        
        logger.info ("get[FIN] caja encontrada: {}", cajas.get(0) );
        return cajas.get(0);
    }
    
    public List<CajaBloqueoTO> getList(UbicacionTO pkUbicacion, Optional<Boolean> vigencia)
    {
        logger.info ("getList[INI] pkUbicacion: {}", pkUbicacion);
        logger.info ("getList[INI] vigencia: {}", vigencia );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put("ubicacion", pkUbicacion);
        parms.put("vigencia", vigencia.orElse(null));
        logger.debug("getList[001] parametros: {}", parms);

        List<CajaBloqueoTO> cajas = mapper.selectCajasBloqueos( parms );
        logger.debug("getList[002] despues de ejecutar el select: {}", cajas.size() );

        logger.info ("getList[FIN] registros encontrados: {}", cajas.size() );
        return cajas;
    }

    @Override
    public boolean esEliminable(CajaBloqueoTO pk)
    {
        logger.info ("isDeleteable[INI] pkCaja: {}", pk);
        
        int relaciones = mapper.childsCajaBloqueo(pk);
        
        logger.info ("isDeleteable[FIN] relaciones: {}", relaciones);
        return relaciones == 0;
    }
}
