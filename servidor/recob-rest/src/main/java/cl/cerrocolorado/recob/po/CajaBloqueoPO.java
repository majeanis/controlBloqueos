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
    public CajaBloqueoTO save(CajaBloqueoTO caja)
    {
        logger.info ("save[INI] caja: {}", caja);

        if( caja.isIdBlank() )
        {
            mapper.insertCajaBloqueo( caja );    
            logger.debug("save[001] después de insertar la caja: {}", caja);
        } else
        {
            mapper.updateCajaBloqueo( caja );
            logger.debug("save[002] después de actualizar la caja: {}", caja);
        }
        
        logger.info ("save[FIN] caja guardada con éxito: {}", caja);
        return caja;
    }

    @Override
    public void delete(CajaBloqueoTO pkCaja)
    {
        logger.info ("delete[INI] pkCaja: {}", pkCaja );
        mapper.deleteCajaBloqueo(pkCaja);
        logger.info ("delete[FIN] despues de eliminar a la caja: {}", pkCaja );
    }

    private CajaBloqueoTO get(Map<String, Object> parms)
    {
        logger.info ("get[INI] parms: {}", parms );
        
        List<CajaBloqueoTO> cajas = mapper.selectCajasBloqueos( parms );
        logger.debug("get[001] despues de ejecutar el select: {}", cajas.size() );
        
        if(cajas.isEmpty())
        {
            logger.info ("get[FIN] no se encontró registro de la caja: {}", parms );
            return null;
        }
        
        logger.info ("get[FIN] caja encontrada: {}", cajas.get(0) );
        return cajas.get(0);
    }
    
    @Override
    public CajaBloqueoTO get(CajaBloqueoTO pk)
    {
        logger.info ("get[INI] pk: {}", pk );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "ubicacion", pk.getUbicacion() );
        parms.put( "numero", pk.getNumero() );
        
        CajaBloqueoTO c = get( parms );
        logger.info ("get[FIN] registro encontrado: {}", c);
        return c;
    }

    @Override
    public CajaBloqueoTO getById(CajaBloqueoTO id)
    {
        logger.info ("getById[INI] id: {}", id );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "ubicacion", id.getUbicacion() );
        parms.put( "id", id.getId() );
        
        CajaBloqueoTO c = get( parms );
        logger.info ("getById[FIN] registro encontrado: {}", c);
        return c;
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
    public boolean isDeleteable(CajaBloqueoTO pk)
    {
        logger.info ("isDeleteable[INI] pkCaja: {}", pk);
        
        long relaciones = mapper.childsCajaBloqueo(pk);
        
        logger.info ("isDeleteable[FIN] relaciones: {}", relaciones);
        return relaciones == 0;
    }
}
