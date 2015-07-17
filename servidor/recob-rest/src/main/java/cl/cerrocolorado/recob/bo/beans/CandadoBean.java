package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.bo.CandadoBO;
import cl.cerrocolorado.recob.po.CandadoPO;
import cl.cerrocolorado.recob.po.TrabajadorPO;
import cl.cerrocolorado.recob.po.UbicacionPO;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Rut;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
@Service("candadoBO")
public class CandadoBean implements CandadoBO
{
    private static final Logger logger = LogManager.getLogger(CandadoBean.class);
    
    @Autowired
    private CandadoPO candadoPO;

    @Autowired
    private UbicacionPO ubicacionPO;
    
    @Autowired
    private TrabajadorPO trabajadorPO;

    @Transaccional
    @Override
    public Respuesta<CandadoTO> guardar(CandadoTO candado) throws Exception
    {
        logger.info ("guardar[INI] candadoo: {}", candado);
        
        Resultado rtdo = new ResultadoProceso();

        if( candado == null )
        {
        	rtdo.addError(this.getClass(), "Debe informar datos del Candado" );
        	logger.info("guardar[FIN] no se informaron datos");
        	return Respuesta.of(rtdo);
        }
        if( candado.getNumero() == null )
        {
            rtdo.addError(this.getClass(), "Debe informa el N° del candado");
        } else if( candado.getNumero() == 0 )
        {
            rtdo.addError(this.getClass(), "N° del candado es inválido");
        }
        if( StringUtils.isBlank(candado.getSerie()) )
        {
            rtdo.addError(this.getClass(), "Debe informar número de serie del candado" );
        }
        if ( candado.getVigente() == null )
        {
            rtdo.addError(this.getClass(), "Debe informar vigencia del candado");
        }

        if( candado.getUso() == null || candado.getUso().getId() == null)
        {
            rtdo.addError(this.getClass(), "Debe informar el uso del candado" );
        }

        if( candado.getUbicacion() == null || candado.getUbicacion().getId() == null )
        {
            rtdo.addError(this.getClass(), "Candado debe estar asociado a una ubicación" );
        } else
        {
	        UbicacionTO ubicacion = ubicacionPO.get(candado.getUbicacion());
	        if( ubicacion == null )
	        {
	            rtdo.addError(this.getClass(), "La ubicación informada no existe [id: #{1}]", String.valueOf(candado.getUbicacion().getId()));
	        }
        }

        if( candado.getPersona() == null || Rut.isBlank(candado.getPersona().getRut() ) )
        {
            rtdo.addError(this.getClass(), "Debe asociar el candado a una Persona" );
        } else
        {
	        PersonaTO persona = trabajadorPO.getPersona(candado.getPersona());
	        if( persona == null )
	        {
	            rtdo.addError(CandadoBean.class, "Persona informada no existe [RUT: #{1}]", candado.getPersona().getRut().toText());
	        }
        }
        
        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] saliendo del método por falla en validaciones: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        // Buscamos la preexistencia del candado
        CandadoTO otroCandado = candadoPO.get( candado );
        if( otroCandado != null ) 
        {
            logger.debug("guardar[001] ya existe registro para el candado: {}", otroCandado );
            candado.setId(otroCandado.getId());
        }

        // Verificamos si existe otro Candado con el mismo N° de Serie
        CandadoTO otroSerie = candadoPO.getBySerie(candado);
        if( otroSerie != null )
        {
            if( candado.getId() == null || !Objects.equals(otroSerie.getId(), candado.getId()) )
            {
                rtdo.addError(this.getClass(), "Ya existe candado con el N° de serie #{1} [Candado: #{2}]", candado.getSerie(), String.valueOf(otroSerie.getNumero()));
                logger.info("guardar[FIN] Se repitió el N° de serie en otro candado: {}", otroSerie);
                return Respuesta.of(rtdo);
            }
        }
        
        // Si llegamos a este punto la Caja puede ser Guardada
        candadoPO.guardar(candado);
        logger.info ("guardar[FIN] candado guardado con exito: {}", candado );
        return Respuesta.of(candado);
    }
    
    @Transaccional
    @Override
    public Resultado eliminar(CandadoTO pkCandado) throws Exception
    {
        logger.info ("eliminar[INI] candado: {}", pkCandado );
        Resultado rtdo = new ResultadoProceso();
        
        if(pkCandado==null)
        {
            logger.info("eliminar[FIN] pkCandado llego en NULL");
        	rtdo.addError(this.getClass(), "Debe informar el candado que desea eliminar" );
        	return rtdo;
        }
        if( pkCandado.getNumero() == null )
        {
            rtdo.addError(this.getClass(), "Debe informar el número del candado" );
        }
        if( pkCandado.getUbicacion() == null || pkCandado.getUbicacion().getId() == null)
        {
            rtdo.addError(this.getClass(), "Debe informar la ubicación del candado" );
        }
        
        CandadoTO candado = candadoPO.get(pkCandado);
        if( candado == null )
        {
            rtdo.addError(this.getClass(), "No existe Candado con N° #{1}", String.valueOf(pkCandado.getNumero()) );
            logger.info ("eliminar[FIN] no existe candado: {}", pkCandado );
            return rtdo;
        }

        // Si llegamos a este punto, entonces es posible la eliminación
        candadoPO.eliminar(candado);
        logger.debug("eliminar[001] despues de eliminar el candado: {}", candado );
        
        rtdo.addMensaje(this.getClass(), "Candado con N° de serie #{1} eliminado con éxito", candado.getSerie() );
        logger.info ("eliminar[FIN] candado eliminado con exito: {} {}", rtdo, candado );
        return rtdo;
    }
    
    @Override
    public Respuesta<CandadoTO> get(CandadoTO pkCandado)
    {
        logger.info ("get[INI] candado: {}", pkCandado );

        Resultado rtdo = new ResultadoProceso();
        
        if( pkCandado == null || 
            pkCandado.getNumero() == null || 
            pkCandado.getUbicacion() == null ||
            pkCandado.getUbicacion().getId() == null)
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación del candado");
            logger.info("get[FIN] faltaron datos en la identificación del candado: {}", pkCandado);
            return Respuesta.of(rtdo);
        }

        CandadoTO candado = candadoPO.get(pkCandado);
        
        logger.info ("get[FIN] resultado busqueda: {}", candado );
        return Respuesta.of(candado);
    }

    @Override
    public Respuesta<CandadoTO> get(UbicacionTO pkUbicacion, String serieCandado)
    {
        logger.info ("get[INI] pkUbicacion: {}", pkUbicacion );
        logger.info ("get[INI] serieCandado: {}", serieCandado );
        
        Resultado rtdo = new ResultadoProceso();
        
        if( pkUbicacion == null || 
            pkUbicacion.getId() == null || 
            serieCandado == null )
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación del candado");
            logger.info("get[FIN] no se informaron todos los filtros: {}-{}", pkUbicacion, serieCandado);
            return Respuesta.of(rtdo);
        }

        CandadoTO pkCandado = new CandadoTO();
        pkCandado.setUbicacion(pkUbicacion);
        pkCandado.setSerie(serieCandado);
        CandadoTO candado = candadoPO.getBySerie(pkCandado);
        
        logger.info ("get[FIN] resultado busqueda: {}", candado );
        return Respuesta.of(candado);
    }

    @Override
    public List<CandadoTO> getVigentes(UbicacionTO pkUbicacion)
    {
        logger.info ("getVigentes[INI] pkUbicacion: {}", pkUbicacion );
        
        if(pkUbicacion == null || pkUbicacion.getId() == null)
        {
            logger.info ("getVigentes[FIN] no se informaron todos los filtros: {}{}", pkUbicacion);
            return new ArrayList<>();
        }

        List<CandadoTO> candados = candadoPO.getList(pkUbicacion, true);
        
        logger.info ("getVigentes[FIN] cantidad registros encontrados: {}", candados.size() );
        return candados;
    }

    @Override
    public List<CandadoTO> getVigentes(UbicacionTO pkUbicacion, PersonaTO pkPersona)
    {
        logger.info ("getVigentes[INI] ubicacion: {}", pkUbicacion );
        logger.info ("getVigentes[INI] persona: {}", pkPersona );

        if( pkUbicacion == null || 
            pkUbicacion.getId() == null ||
            pkPersona == null ||
            pkPersona.getRut() == null)
        {
            logger.info ("getVigentes[FIN] no se informaron todos los filtros: {} {}", pkUbicacion, pkPersona );
            return new ArrayList<>();
        }

        List<CandadoTO> candados = candadoPO.getList(pkUbicacion, pkPersona, true);
        
        logger.info ("getVigentes[FIN] cantidad registros encontrados: {}", candados.size() );
        return candados;
    }

    @Override
    public List<CandadoTO> getTodos(UbicacionTO pkUbicacion)
    {
        logger.info ("getTodos[INI] ubicacion: {}", pkUbicacion );

        if( pkUbicacion == null || pkUbicacion.getId() == null)
        {
            logger.info ("getVigentes[FIN] no se informaron todos los filtros: {} {}", pkUbicacion );
            return new ArrayList<>();
        }

        List<CandadoTO> candados = candadoPO.getList(pkUbicacion, null);
        
        logger.info ("getTodos[FIN] cantidad registros encontrados: {}", candados.size() );
        return candados;
    }

	@Override
	public List<CandadoTO> getTodos(UbicacionTO pkUbicacion, PersonaTO pkPersona) {
        logger.info ("getTodos[INI] pkUbicacion: {}", pkUbicacion );
        logger.info ("getTodos[INI] pkPersona: {}", pkPersona );

        if( pkUbicacion == null || 
            pkUbicacion.getId() == null ||
            pkPersona == null ||
            pkPersona.getRut() == null)
        {
            logger.info ("getTodos[FIN] no se informaron todos los filtros: {} {}", pkUbicacion, pkPersona );
            return new ArrayList<>();
        }

        List<CandadoTO> candados = candadoPO.getList(pkUbicacion, pkPersona, null);
        
        logger.info ("getTodos[FIN] cantidad registros encontrados: {}", candados.size() );
        return candados;
	}
}
