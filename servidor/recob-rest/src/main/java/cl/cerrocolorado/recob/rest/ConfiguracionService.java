package cl.cerrocolorado.recob.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.cerrocolorado.recob.bo.utils.FactoryBO;
import cl.cerrocolorado.recob.bo.UbicacionBO;
import cl.cerrocolorado.recob.rest.utils.RespGenerica;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.EquipoTagsTO;
import cl.cerrocolorado.recob.to.FuncionBloqueoTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.to.UsoCandadoTO;
import cl.cerrocolorado.recob.utils.JsonUtils;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Rut;
import cl.cerrocolorado.recob.utils.mensajes.ParsearJsonError;
import java.util.Arrays;
import java.util.Optional;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;

@Path("configuracion")
public class ConfiguracionService
{
    private static final Logger logger = LogManager.getLogger(ConfiguracionService.class);
    
    private static final UbicacionBO ubicacionBO;

    static
    {
        ubicacionBO = FactoryBO.getUbicacionBO();
    }

    @Path("cajasBloqueo")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verCajasBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("vigentes") Boolean vigentes)
    {
        logger.info ("verCajasBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("verCajasBloqueo[INI] vigentes: {}", vigentes);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verCajasBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            Optional<Boolean> vigencia = Optional.ofNullable(vigentes);
            Respuesta<List<CajaBloqueoTO>> r1 = FactoryBO.getCajaBloqueoBO().getCajas(ubicacion,vigencia);
            
            logger.info("verCajasBloqueo[FIN] retorno de todos los registros: {}", r1);
            return RespGenerica.of(r1);
        } catch(Exception e)
        {
            logger.error("verCajasBloqueo[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("cajasBloqueo/{numeroCaja}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("numeroCaja") Integer numeroCaja)
    {
        logger.info ("verCajaBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("verCajaBloqueo[INI] numeroCaja: {}", numeroCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CajaBloqueoTO caja = new CajaBloqueoTO();
            caja.setUbicacion(ubicacion);
            caja.setNumero(numeroCaja);
            Respuesta<CajaBloqueoTO> r = FactoryBO.getCajaBloqueoBO().get(caja);

            logger.info("verCajaBloqueo[FIN] retorno de un registro: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verCajaBloqueo[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("cajasBloqueo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    public RespGenerica crearCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("caja") String jsonCaja)
    {
    	logger.info ("crearCajaBloqueo[INI] token: {}", tokenUbicacion);
    	logger.info ("crearCajaBloqueo[INI] jsonCaja: {}", jsonCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("crearCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CajaBloqueoTO caja = JsonUtils.fromJson(jsonCaja, CajaBloqueoTO.class);
            if( caja == null )
            {
                logger.debug("crearCajaBloqueo[001] no se pudo parsear el JSON: {}", jsonCaja);

                RespGenerica r = RespGenerica.of(new ParsearJsonError(this.getClass(), "Caja"));
                logger.info ("crearCajaBloqueo[FIN] no se pudo parsear el JSON: {}", r);
                return r;
            }

            caja.setUbicacion(ubicacion);
            logger.debug("crearCajaBloqueo[002] después de parsear el JSON: {}", caja);

            Respuesta<CajaBloqueoTO> r = FactoryBO.getCajaBloqueoBO().crear(caja);

            logger.info("crearCajaBloqueo[FIN] resultado registro caja: {}", r);
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("crearCajaBloqueo[ERR] al guardar caja de bloqueo:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("cajasBloqueo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @PUT
    public RespGenerica modificarCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("caja") String jsonCaja)
    {
    	logger.info ("modificarCajaBloqueo[INI] token: {}", tokenUbicacion);
    	logger.info ("modificarCajaBloqueo[INI] jsonCaja: {}", jsonCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("modificarCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CajaBloqueoTO caja = JsonUtils.fromJson(jsonCaja, CajaBloqueoTO.class);
            if( caja == null )
            {
                logger.debug("modificarCajaBloqueo[001] no se pudo parsear el JSON: {}", jsonCaja);

                RespGenerica r = RespGenerica.of(new ParsearJsonError(this.getClass(), "Caja"));
                logger.info ("modificarCajaBloqueo[FIN] no se pudo parsear el JSON: {}", r);
                return r;
            }

            caja.setUbicacion(ubicacion);
            logger.debug("modificarCajaBloqueo[002] después de parsear el JSON: {}", caja);

            Respuesta<CajaBloqueoTO> r = FactoryBO.getCajaBloqueoBO().modificar(caja);

            logger.info("modificarCajaBloqueo[FIN] resultado registro caja: {}", r);
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("modificarCajaBloqueo[ERR] al guardar caja de bloqueo:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("cajasBloqueo/{numeroCaja}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("numeroCaja") Integer numeroCaja)
    {
        logger.info ("eliminarCajaBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarCajaBloqueo[INI] numeroCaja: {}", numeroCaja);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("eliminarCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CajaBloqueoTO caja = new CajaBloqueoTO();
            caja.setNumero(numeroCaja);
            caja.setUbicacion(ubicacion);

            Respuesta<CajaBloqueoTO> r = FactoryBO.getCajaBloqueoBO().eliminar(caja);
            
            logger.info("eliminarCajaBloqueo[FIN] resultado de la eliminación: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("eliminarCajaBloqueo[ERR] al eliminar la caja:", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("candados")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verCandados(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("vigentes") Boolean vigentes)
    {
        logger.info ("verCandados[INI] token: {}", tokenUbicacion);
        logger.info ("verCandados[INI] vigentes: {}", vigentes);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verCandados[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            Optional<Boolean> vigencia = Optional.ofNullable(vigentes);
            Respuesta<List<CandadoTO>> r = FactoryBO.getCandadoBO().getCandados(ubicacion, vigencia);

            logger.info("verCandados[FIN] retorno de todos los registros: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verCandados[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("candados/{numeroCandado}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verCandado(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("numeroCandado") Integer numeroCandado)
    {
        logger.info ("verCandado[INI] token: {}", tokenUbicacion);
        logger.info ("verCandado[INI] numeroCandado: {}", numeroCandado);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verCandado[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CandadoTO candado = new CandadoTO();
            candado.setNumero(numeroCandado);
            candado.setUbicacion(ubicacion);
            Respuesta<CandadoTO> r = FactoryBO.getCandadoBO().get(candado);

            logger.info("verCandado[FIN] retorno de un registro: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verCandado[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("candados")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    public RespGenerica crearCandado(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("candado") String jsonCandado)
    {
    	logger.info ("crearCandado[INI] token: {}", tokenUbicacion);
    	logger.info ("crearCandado[INI] jsonCandado: {}", jsonCandado);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("crearCandado[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CandadoTO candado = JsonUtils.fromJson(jsonCandado, CandadoTO.class);
            if( candado == null )
            {
                logger.info("crearCandado[FIN] no se pudo parsear el JSON: {}", jsonCandado);
                return null;
            }

            candado.setUbicacion(ubicacion);
            logger.debug("crearCandado[001] después de parsear el JSON: {}", candado);

            Respuesta<CandadoTO> r = FactoryBO.getCandadoBO().crear(candado);

            logger.info("crearCandado[FIN] resultado del guardado: {}", r);
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("crearCandado[ERR] al guardar candado:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("candados")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @PUT
    public RespGenerica modificarCandado(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("candado") String jsonCandado)
    {
    	logger.info ("modificarCandado[INI] token: {}", tokenUbicacion);
    	logger.info ("modificarCandado[INI] jsonCandado: {}", jsonCandado);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("modificarCandado[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CandadoTO candado = JsonUtils.fromJson(jsonCandado, CandadoTO.class);
            if( candado == null )
            {
                logger.info("modificarCandado[FIN] no se pudo parsear el JSON: {}", jsonCandado);
                return null;
            }

            candado.setUbicacion(ubicacion);
            logger.debug("modificarCandado[001] después de parsear el JSON: {}", candado);

            Respuesta<CandadoTO> r = FactoryBO.getCandadoBO().modificar(candado);

            logger.info("modificarCandado[FIN] resultado del guardado: {}", r);
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("modificarCandado[ERR] al guardar candado:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("candados/{numeroCandado}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarCandado(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("numeroCandado") Integer numeroCandado)
    {
        logger.info ("eliminarCandado[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarCandado[INI] numeroCandado: {}", numeroCandado);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("eliminarCandado[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CandadoTO candado = new CandadoTO();
            candado.setNumero(numeroCandado);
            candado.setUbicacion(ubicacion);

            Respuesta<CandadoTO> r = FactoryBO.getCandadoBO().eliminar(candado);
            
            logger.info("eliminarCandado[FIN] resultado de la eliminación: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("eliminarCandado[ERR] al eliminar el candado:", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("candados/usos")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verUsosCandado(
            @HeaderParam("token") String tokenUbicacion)
    {
        logger.info ("verUsosCandado[INI] token: {}", tokenUbicacion);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verUsosCandado[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            Respuesta<List<UsoCandadoTO>> r = FactoryBO.getCandadoBO().getUsosCandado(Optional.of(Boolean.TRUE));
            logger.info("verUsosCandado[FIN] registros retornados: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verUsosCandado[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }
    
    @Path("equipos")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verEquipos(
    		@HeaderParam("token") String tokenUbicacion,
            @QueryParam("vigentes") Boolean vigentes)
    {
        logger.info ("verEquipos[INI] token: {}", tokenUbicacion);
        logger.info ("verEquipos[INI] vigentes: {}", vigentes);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verEquipos[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().get();
            Optional<Boolean> vigencia = Optional.ofNullable(vigentes);
            Respuesta<List<EquipoTO>> r = FactoryBO.getEquipoBO().getEquipos(ubicacion, vigencia);

            logger.info("verEquipos[FIN] retorno de todos los registros: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verEquipos[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("equipos/{codigoEquipo}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("codigoEquipo") String codigoEquipo)
    {
        logger.info ("verEquipo[INI] token: {}", tokenUbicacion);
        logger.info ("verEquipo[INI] codigoEquipo: {}", codigoEquipo);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().get();
            EquipoTO equipo = new EquipoTO();
            equipo.setCodigo(codigoEquipo);
            equipo.setUbicacion(ubicacion);
            Respuesta<EquipoTagsTO> r = FactoryBO.getEquipoBO().getEquipo(equipo);

            logger.info("verEquipo[FIN] retorno de un registro: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verEquipo[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("equipos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    public RespGenerica crearEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("equipo") String jsonEquipo)
    {
    	logger.info ("crearEquipo[INI] token: {}", tokenUbicacion);
    	logger.info ("crearEquipo[INI] equipo: {}", jsonEquipo);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("crearEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            EquipoTagsTO equipo = JsonUtils.fromJson(jsonEquipo, EquipoTagsTO.class);
            if( equipo == null )
            {
                logger.info ("crearEquipo[FIN] no se pudo parsear el JSON: {}", jsonEquipo);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Equipo"));
            }
            
            equipo.setUbicacion(respUbic.getContenido().orElse(null));
            logger.debug("crearEquipo[001] después de parsear el JSON: {}", equipo);

            Respuesta<EquipoTO> resp = FactoryBO.getEquipoBO().crear(equipo);
	        logger.info ("crearEquipo[FIN] equipo modificado on éxito: {}", resp);
			return RespGenerica.of(resp);
        } catch (Exception e) 
        {
			logger.error("crearEquipo[ERR] al guardar equipo:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }
    
    @Path("equipos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @PUT
    public RespGenerica modificarEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("equipo") String jsonEquipo)
    {
    	logger.info ("modificarEquipo[INI] token: {}", tokenUbicacion);
    	logger.info ("modificarEquipo[INI] equipo: {}", jsonEquipo);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("modificarEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            EquipoTagsTO equipo = JsonUtils.fromJson(jsonEquipo, EquipoTagsTO.class);
            if( equipo == null )
            {
                logger.info ("modificarEquipo[FIN] no se pudo parsear el JSON: {}", jsonEquipo);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Equipo"));
            }
            
            equipo.setUbicacion(respUbic.getContenido().orElse(null));
            logger.debug("modificarEquipo[001] después de parsear el JSON: {}", equipo);

            Respuesta<EquipoTO> resp = FactoryBO.getEquipoBO().modificar(equipo);
	        logger.info ("modificarEquipo[FIN] equipo modificado on éxito: {}", resp);
			return RespGenerica.of(resp);
        } catch (Exception e) 
        {
			logger.error("modificarEquipo[ERR] al guardar equipo:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("equipos/{codigoEquipo}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("codigoEquipo") String codigoEquipo)
    {
        logger.info ("eliminarEquipo[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarEquipo[INI] codigoEquipo: {}", codigoEquipo);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("eliminarEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            EquipoTO equipo = new EquipoTO();
            equipo.setCodigo(codigoEquipo);
            equipo.setUbicacion(ubicacion);
        
            Respuesta<EquipoTO> r = FactoryBO.getEquipoBO().eliminar(equipo);
            
            logger.info("eliminarEquipo[FIN] resultado de la eliminación: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("eliminarEquipo[ERR] al eliminar equipo:", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("equipos/{codigoEquipo}/tags")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verTags(
    		@HeaderParam("token") String tokenUbicacion,
            @PathParam  ("codigoEquipo") String codigoEquipo,
    		@QueryParam ("vigentes") Boolean vigentes)
    {
        logger.info ("verTags[INI] token: {}" , tokenUbicacion);
        logger.info ("verTags[INI] codigoEquipo: {}", codigoEquipo);
        logger.info ("verTags[INI] vigentes: {}", vigentes);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verTags[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            Optional<Boolean> vigencia = Optional.of(vigentes);
            EquipoTO pkEquipo = new EquipoTO();
            pkEquipo.setUbicacion(ubicacion);
            pkEquipo.setCodigo(codigoEquipo);
            logger.debug("verTags[001] antes de ejecutar el método BO: {}", pkEquipo);
            
            Respuesta<List<TagTO>> r = FactoryBO.getEquipoBO().getTags(pkEquipo, vigencia);
            logger.info("verTags[FIN] retorno de todos los registros: {}", r);
            return RespGenerica.of(r);

        } catch(Exception e)
        {
            logger.error("verTags[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("equipos/{codigoEquipo}/tags/{numeroTag}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verTag(
    		@HeaderParam("token") String tokenUbicacion,
            @PathParam("codigoEquipo") String codigoEquipo,
    		@PathParam("codigoTag") String codigoTag)
    {
        logger.info ("verTag[INI] token: {}" , tokenUbicacion);
        logger.info ("verTag[INI] codigoEquipo: {}", codigoEquipo);
        logger.info ("verTag[INI] codigoTag: {}", codigoTag);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verTag[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            EquipoTO equipo = new EquipoTO();
            equipo.setUbicacion(ubicacion);
            equipo.setCodigo(codigoEquipo);
            TagTO tag = new TagTO();
            tag.setEquipo(equipo);
            tag.setCodigo(codigoTag);
            logger.debug("verTag[001] antes de ejecutar al método BO: {}", tag);
            
            Respuesta<TagTO> r = FactoryBO.getEquipoBO().getTag(tag);

            logger.info("verTag[FIN] retorno de un registro: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verTag[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }
    
    @Path("empresas")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verEmpresas(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("vigentes") Boolean vigentes)
    {
        logger.info ("verEmpresas[INI] token: {}", tokenUbicacion);
        logger.info ("verEmpresas[INI] vigentes: {}", vigentes);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verEmpresas[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            Optional<Boolean> vigencia = Optional.ofNullable(vigentes);
            Respuesta<List<EmpresaTO>> r1 = FactoryBO.getEmpresaBO().getEmpresas(vigencia);
            logger.info("verEmpresas[FIN] retorno de todos los registros: {}", r1.getResultado());
            return RespGenerica.of(r1);
        } catch(Exception e)
        {
            logger.error("verEmpresas[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("empresas/{rutEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verEmpresa(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("rut") String rutEmpresa)
    {
        logger.info ("verEmpresa[INI] token: {}", tokenUbicacion);
        logger.info ("verEmpresa[INI] rut: {}", rutEmpresa);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verEmpresa[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            EmpresaTO empresa = new EmpresaTO();
            empresa.setRut(Rut.valueOf(rutEmpresa));
            Respuesta<EmpresaTO> r = FactoryBO.getEmpresaBO().get(empresa);

            logger.info("verEmpresa[FIN] retorno de un registro: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verEmpresa[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("empresas")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    public RespGenerica crearEmpresa(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("empresa") String jsonEmpresa)
    {
    	logger.info ("crearEmpresa[INI] token: {}", tokenUbicacion);
    	logger.info ("crearEmpresa[INI] empresa: {}", jsonEmpresa);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("crearEmpresa[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            EmpresaTO empresa = JsonUtils.fromJson(jsonEmpresa, EmpresaTO.class);
            if( empresa == null )
            {
                logger.info ("crearEmpresa[FIN] no se pudo parsear el JSON: {}", jsonEmpresa);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Empresa"));
            }
            
            logger.debug("crearEmpresa[001] después de parsear el JSON: {}", empresa);

            Respuesta<EmpresaTO> r = FactoryBO.getEmpresaBO().crear(empresa);
	        logger.info ("crearEmpresa[FIN] empresa registrada: {}", r);
			return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("crearEmpresa[ERR] al guardar empresa:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("empresas")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @PUT
    public RespGenerica modificarEmpresa(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("empresa") String jsonEmpresa)
    {
    	logger.info ("modificarEmpresa[INI] token: {}", tokenUbicacion);
    	logger.info ("modificarEmpresa[INI] empresa: {}", jsonEmpresa);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("modificarEmpresa[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            EmpresaTO empresa = JsonUtils.fromJson(jsonEmpresa, EmpresaTO.class);
            if( empresa == null )
            {
                logger.info ("modificarEmpresa[FIN] no se pudo parsear el JSON: {}", jsonEmpresa);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Empresa"));
            }
            
            logger.debug("modificarEmpresa[001] después de parsear el JSON: {}", empresa);

            Respuesta<EmpresaTO> r = FactoryBO.getEmpresaBO().modificar(empresa);
	        logger.info ("modificarEmpresa[FIN] empresa registrada: {}", r);
			return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("modificarEmpresa[ERR] al guardar empresa:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("empresas/{rutEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarEmpresa(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("rutEmpresa") String rutEmpresa)
    {
        logger.info ("eliminarEmpresa[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarEmpresa[INI] rutEmpresa: {}", rutEmpresa);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("eliminarEmpresa[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
        try
        {
            EmpresaTO empresa = new EmpresaTO();
            empresa.setRut(Rut.valueOf(rutEmpresa));
        
            Respuesta<EmpresaTO> r = FactoryBO.getEmpresaBO().eliminar(empresa);
            
            logger.info("eliminarEmpresa[FIN] resultado de la eliminación: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("eliminarEmpresa[ERR] al eliminar empresa:", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("trabajadores/{rutTrabajador}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarTrabajador(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("rutTrabajador") String rutTrabajador)
    {
        logger.info ("eliminarTrabajador[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarTrabajador[INI] rutTrabajador: {}", rutTrabajador);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("eliminarTrabajador[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
        try
        {
            TrabajadorTO trabajador = new TrabajadorTO();
            trabajador.setRut(Rut.valueOf(rutTrabajador));
        
            Respuesta<TrabajadorTO> r = FactoryBO.getTrabajadorBO().eliminar(trabajador);
            
            logger.info("eliminarTrabajador[FIN] resultado de la eliminación: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("eliminarTrabajador[ERR] al eliminar empresa:", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("trabajadores")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    public RespGenerica crearTrabajador(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("trabajador") String jsonTrabajador)
    {
    	logger.info ("crearTrabajador[INI] token: {}", tokenUbicacion);
    	logger.info ("crearTrabajador[INI] trabajador: {}", jsonTrabajador);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("crearTrabajador[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            TrabajadorTO trabajador = JsonUtils.fromJson(jsonTrabajador, TrabajadorTO.class);
            logger.debug("crearTrabajador[001] después de parsear el JSON: {}", trabajador);
            
            if( trabajador == null )
            {
                logger.info ("modificarTrabajador[FIN] no se pudo parsear el JSON: {}", jsonTrabajador);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Trabajador"));
            }

            Respuesta<TrabajadorTO> r = FactoryBO.getTrabajadorBO().crear(trabajador);
	        logger.info ("crearTrabajador[FIN] trabajador creado: {}", r);
			return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("crearTrabajador[ERR] al crear trabajador:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("trabajadores")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @PUT
    public RespGenerica modificarTrabajador(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("trabajador") String jsonTrabajador)
    {
    	logger.info ("modificarTrabajador[INI] token: {}", tokenUbicacion);
    	logger.info ("modificarTrabajador[INI] trabajador: {}", jsonTrabajador);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("modificarTrabajador[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            TrabajadorTO trabajador = JsonUtils.fromJson(jsonTrabajador, TrabajadorTO.class);
            logger.debug("modificarTrabajador[001] después de parsear el JSON: {}", trabajador);
            
            if( trabajador == null )
            {
                logger.info ("modificarTrabajador[FIN] no se pudo parsear el JSON: {}", jsonTrabajador);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Trabajador"));
            }

            Respuesta<TrabajadorTO> r = FactoryBO.getTrabajadorBO().modificar(trabajador);
	        logger.info ("modificarTrabajador[FIN] trabajador registrado: {}", r);
			return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("modificarTrabajador[ERR] al guardar trabajador:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("trabajadores")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verTrabajadores(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("vigentes") Boolean vigentes)
    {
        logger.info ("verTrabajadores[INI] token: {}", tokenUbicacion);
        logger.info ("verTrabajadores[INI] vigentes: {}", vigentes);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verTrabajadores[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            Optional<Boolean> vigencia = Optional.ofNullable(vigentes);
            Respuesta<List<TrabajadorTO>> r = FactoryBO.getTrabajadorBO().getTrabajadores(vigencia);
            logger.info("verTrabajadores[FIN] retorno de todos los registros: {}", r.getResultado());
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verTrabajadores[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("trabajadores/{rutTrabajador}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verTrabajador(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("rutTrabajador") String rutTrabajador)
    {
        logger.info ("verTrabajador[INI] token: {}", tokenUbicacion);
        logger.info ("verTrabajador[INI] rutTrabajador: {}", rutTrabajador);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verTrabajador[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            TrabajadorTO trabajador = new TrabajadorTO();
            trabajador.setRut(Rut.valueOf(rutTrabajador));
            Respuesta<TrabajadorTO> r = FactoryBO.getTrabajadorBO().get(trabajador);

            logger.info("verTrabajador[FIN] retorno de un registro: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verTrabajadores[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }
    
    @Path("funcionesBloqueo")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verFuncionesBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("vigentes") Boolean vigentes)
    {
        logger.info ("verFuncionesBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("verFuncionesBloqueo[INI] vigentes: {}", vigentes);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verFuncionesBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            Optional<Boolean> vigencia = Optional.ofNullable(vigentes);
            Respuesta<List<FuncionBloqueoTO>> r = FactoryBO.getUbicacionBO().getFunciones(vigencia);
            logger.info("verFuncionesBloqueo[FIN] retorno de todos los registros: {}", r.getResultado());
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verFuncionesBloqueo[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }
}
