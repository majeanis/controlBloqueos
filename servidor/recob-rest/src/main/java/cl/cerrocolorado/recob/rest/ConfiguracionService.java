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
import cl.cerrocolorado.recob.utils.ToStringUtils;
import cl.cerrocolorado.recob.utils.mensajes.ParsearJsonError;
import java.util.Arrays;
import java.util.Optional;
import javax.ws.rs.FormParam;
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
            Respuesta<List<CajaBloqueoTO>> r1 = FactoryBO.getCajaBloqueoBO().getTodos(ubicacion,vigencia);
            
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
    @PUT
    public RespGenerica guardarCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("caja") String jsonCaja)
    {
    	logger.info ("guardarCajaBloqueo[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarCajaBloqueo[INI] jsonCaja: {}", jsonCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CajaBloqueoTO caja = JsonUtils.fromJson(jsonCaja, CajaBloqueoTO.class);
            if( caja == null )
            {
                logger.info("guardarCajaBloqueo[FIN] no se pudo parsear el JSON: {}", jsonCaja);
                RespGenerica r4 = RespGenerica.of(new ParsearJsonError(this.getClass(), "Caja"));
                logger.info("guardarCajaBloqueo[FIN] no se pudo parsear el JSON2: {}", ToStringUtils.toString(r4));
                return r4;
            }

            caja.setUbicacion(ubicacion);
            logger.debug("guardarCajaBloqueo[001] después de parsear el JSON: {}", caja);

            Respuesta<CajaBloqueoTO> r = FactoryBO.getCajaBloqueoBO().guardar(caja);

            logger.info("guardarCajaBloqueo[FIN] resultado registro caja: {}", r);
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("guardarCajaBloqueo[ERR] al guardar caja de bloqueo:", e);
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
            Respuesta<List<CandadoTO>> r = FactoryBO.getCandadoBO().getTodos(ubicacion, vigencia);

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
    @PUT
    public RespGenerica guardarCandado(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("candado") String jsonCandado)
    {
    	logger.info ("guardarCandado[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarCandado[INI] jsonCandado: {}", jsonCandado);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarCandado[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CandadoTO candado = JsonUtils.fromJson(jsonCandado, CandadoTO.class);
            if( candado == null )
            {
                logger.info("guardarCandado[FIN] no se pudo parsear el JSON: {}", jsonCandado);
                return null;
            }

            candado.setUbicacion(ubicacion);
            logger.debug("guardarCandado[001] después de parsear el JSON: {}", candado);

            Respuesta<CandadoTO> r = FactoryBO.getCandadoBO().guardar(candado);

            logger.info("guardarCandado[FIN] resultado del guardado: {}", r);
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("guardarCandado[ERR] al guardar candado:", e);
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
            Respuesta<List<EquipoTO>> r = FactoryBO.getEquipoBO().getTodos(ubicacion, vigencia);

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
            Respuesta<EquipoTagsTO> r = FactoryBO.getEquipoBO().get(equipo);

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
    @PUT
    public RespGenerica guardarEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("equipo") String jsonEquipo,
            @FormParam("tags") String jsonTags)
    {
    	logger.info ("guardarEquipo[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarEquipo[INI] equipo: {}", jsonEquipo);
        logger.info ("guardarEquipo[INI] tags: {}", jsonTags);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            EquipoTagsTO equipo = JsonUtils.fromJson(jsonEquipo, EquipoTagsTO.class);
            if( equipo == null )
            {
                logger.info ("guardarEquipo[FIN] no se pudo parsear el JSON: {}", jsonEquipo);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Equipo"));
            }
            if( jsonTags != null )
            {
                TagTO[] tags = JsonUtils.fromJson(jsonTags, TagTO[].class);
                equipo.setTags(Arrays.asList(tags));
            }
            
            equipo.setUbicacion(respUbic.getContenido().orElse(null));
            logger.debug("guardarEquipo[001] después de parsear el JSON: {}", equipo);

            Respuesta<EquipoTagsTO> resp = FactoryBO.getEquipoBO().guardar(equipo);
	        logger.info ("guardarEquipo[FIN] equipo registrado: {}", resp);
			return RespGenerica.of(resp);
        } catch (Exception e) 
        {
			logger.error("guardarEquipo[ERR] al guardar equipo:", e);
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
            
            Respuesta<List<TagTO>> r = FactoryBO.getEquipoBO().getTagsTodos(pkEquipo, vigencia);
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
    		@PathParam("numeroTag") Integer numeroTag)
    {
        logger.info ("verTag[INI] token: {}" , tokenUbicacion);
        logger.info ("verTag[INI] codigoEquipo: {}", codigoEquipo);
        logger.info ("verTag[INI] numeroTag: {}", numeroTag);

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
            tag.setNumero(numeroTag);
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
            Respuesta<List<EmpresaTO>> r1 = FactoryBO.getEmpresaBO().getTodos(vigencia);
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
    @PUT
    public RespGenerica guardarEmpresa(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("empresa") String jsonEmpresa)
    {
    	logger.info ("guardarEmpresa[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarEmpresa[INI] empresa: {}", jsonEmpresa);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarEmpresa[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            EmpresaTO empresa = JsonUtils.fromJson(jsonEmpresa, EmpresaTO.class);
            if( empresa == null )
            {
                logger.info ("guardarEmpresa[FIN] no se pudo parsear el JSON: {}", jsonEmpresa);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Empresa"));
            }
            
            logger.debug("guardarEmpresa[001] después de parsear el JSON: {}", empresa);

            Respuesta<EmpresaTO> r = FactoryBO.getEmpresaBO().guardar(empresa);
	        logger.info ("guardarEmpresa[FIN] empresa registrada: {}", r);
			return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("guardarEmpresa[ERR] al guardar empresa:", e);
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
    @PUT
    public RespGenerica guardarTrabajador(
    		@HeaderParam("token") String tokenUbicacion,
    		@FormParam("trabajador") String jsonTrabajador)
    {
    	logger.info ("guardarTrabajador[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarTrabajador[INI] trabajador: {}", jsonTrabajador);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarTrabajador[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            TrabajadorTO trabajador = JsonUtils.fromJson(jsonTrabajador, TrabajadorTO.class);
            logger.debug("guardarTrabajador[001] después de parsear el JSON: {}", trabajador);
            
            if( trabajador == null )
            {
                logger.info ("guardarTrabajador[FIN] no se pudo parsear el JSON: {}", jsonTrabajador);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Trabajador"));
            }

            Respuesta<TrabajadorTO> r = FactoryBO.getTrabajadorBO().guardar(trabajador);
	        logger.info ("guardarTrabajador[FIN] trabajador registrado: {}", r);
			return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("guardarTrabajador[ERR] al guardar trabajador:", e);
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
            Respuesta<List<TrabajadorTO>> r = FactoryBO.getTrabajadorBO().getTodos(vigencia);
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
