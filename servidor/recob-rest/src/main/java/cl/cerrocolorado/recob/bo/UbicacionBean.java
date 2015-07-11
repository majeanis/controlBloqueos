package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.po.UbicacionPO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
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
            rtdo.addError(UbicacionBean.class, "Debe informar el token asociado a la ubicacion" );
            logger.info ("validarToken[FIN] no se informo el token: {}", tokenUbicacion );
            return new Respuesta<>(rtdo);
        }

        UbicacionTO ubicacion = this.get(tokenUbicacion);
        if( ubicacion == null )
        {
            rtdo.addError(UbicacionBean.class, "No existe ubicacion para el token informado" );
        }
        
        logger.info ("validarToken[FIN] resultado validacion: {}-{}", rtdo, ubicacion );
        return new Respuesta<>(rtdo,ubicacion);
    }
}
