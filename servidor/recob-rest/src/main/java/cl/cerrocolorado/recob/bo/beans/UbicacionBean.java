package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.bo.UbicacionBO;
import cl.cerrocolorado.recob.po.UbicacionPO;
import cl.cerrocolorado.recob.to.FuncionBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.mensajes.RegistrosQueryInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;
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
@Service("ubicacionBO")
public class UbicacionBean implements UbicacionBO
{
    private static final Logger logger = LogManager.getLogger(UbicacionBean.class);
    
    @Autowired
    private UbicacionPO ubicacionPO;
    
    @Override
    public UbicacionTO get(String tokenUbicacion)
    {
        logger.info ("get[INI] tokenUbicacion: {}", tokenUbicacion );

        UbicacionTO ubicacion = new UbicacionTO();
        ubicacion.setToken(tokenUbicacion);
        ubicacion = ubicacionPO.get(ubicacion);
        
        logger.info ("get[FIN] resultado busqueda: {}", ubicacion );
        return ubicacion;
    }

    @Override
    public Respuesta<UbicacionTO> validarToken(String tokenUbicacion)
    {
        logger.info ("validarToken[INI] tokenUbicacion: {}", tokenUbicacion );
        Resultado rtdo = new ResultadoProceso();
        
        if( StringUtils.isBlank(tokenUbicacion))
        {
            rtdo.addError(UbicacionBean.class, "Debe informar el token asociado a la ubicación" );
            logger.info ("validarToken[FIN] no se informo el token: {}", tokenUbicacion );
            return Respuesta.of(rtdo);
        }

        UbicacionTO ubicacion = this.get(tokenUbicacion);
        if( ubicacion == null )
        {
            rtdo.addError(UbicacionBean.class, "No existe ubicación para el token informado" );
        }
        
        logger.info ("validarToken[FIN] resultado validación: {}-{}", rtdo, ubicacion );
        return Respuesta.of(rtdo,ubicacion);
    }

    @Override
    public Respuesta<List<FuncionBloqueoTO>> getFunciones(Optional<Boolean> vigencia)
    {
        logger.info("getFunciones[INI] vigencia: {}", vigencia);
        Resultado rtdo = new ResultadoProceso();
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("vigencia", vigencia.orElse(null));
        
        List<FuncionBloqueoTO> lista = ubicacionPO.getFunciones(vigencia);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getFunciones[FIN] cantidad de registros retornados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Override
    public Respuesta<UbicacionTO> crear(UbicacionTO data) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Respuesta<UbicacionTO> modificar(UbicacionTO data) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Respuesta<UbicacionTO> eliminar(UbicacionTO pk) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Respuesta<UbicacionTO> get(UbicacionTO pk)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
