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
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.Rut;
import cl.cerrocolorado.recob.utils.ToStringUtils;
import cl.cerrocolorado.recob.utils.mensajes.ParsearJsonError;
import java.util.Arrays;
import java.util.Optional;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;

@Path("configuracion")
public class ConfiguracionService
{
    private static final Logger logger = LogManager.getLogger(ConfiguracionService.class);
    
    private static final UbicacionBO ubicacionBO;
    // private static final String token = "ec4b8544-1c73-11e5-9840-080027465435";

    private static final int VER_TODOS = 1;
    private static final int VER_VIGENTES = 2;
    private static final int VER_UNO = 3;
    
    static
    {
        ubicacionBO = FactoryBO.getUbicacionBO();
    }

    @Path("cajasBloqueo")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verCajasBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@DefaultValue("0") @QueryParam("accion") Integer accion,
    		@DefaultValue("0") @QueryParam("numero") Integer numeroCaja)
    {
        logger.info ("verCajasBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("verCajasBloqueo[INI] accion: {}", accion);
        logger.info ("verCajasBloqueo[INI] numeroCaja: {}", numeroCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verCajasBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<CajaBloqueoTO>> r1 = FactoryBO.getCajaBloqueoBO().getTodos(ubicacion,Optional.empty());
                    logger.info("verCajasBloqueo[FIN] retorno de todos los registros: {}", r1);
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<CajaBloqueoTO>> r2 = FactoryBO.getCajaBloqueoBO().getVigentes(ubicacion);
                    logger.info("verCajasBloqueo[FIN] retorno de registros vigentes: {}", r2);
                    return RespGenerica.of(r2);

                case VER_UNO:
                    CajaBloqueoTO caja = new CajaBloqueoTO();
                    caja.setNumero(numeroCaja);
                    caja.setUbicacion(ubicacion);
                    Respuesta<CajaBloqueoTO> r3 = FactoryBO.getCajaBloqueoBO().get(caja);

                    logger.info("verCajasBloqueo[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
            }
        } catch(Exception e)
        {
            logger.error("verCajasBloqueo[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verCajasBloqueo[FIN] código de acción inválido: {}", accion);
        return null;
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

    @Path("cajasBloqueo/{numero}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("numero") Integer numeroCaja)
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
    		@QueryParam("accion") Integer accion,
    		@QueryParam("numero") Integer numeroCandado)
    {
        logger.info ("verCandados[INI] token: {}", tokenUbicacion);
        logger.info ("verCandados[INI] accion: {}", accion);
        logger.info ("verCandados[INI] numeroCandado: {}", numeroCandado);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verCandados[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<CandadoTO>> r1 = FactoryBO.getCandadoBO().getTodos(ubicacion,Optional.empty());
                    logger.info("verCandados[FIN] retorno de todos los registros: {}", r1);
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<CandadoTO>> r2 = FactoryBO.getCandadoBO().getVigentes(ubicacion);
                    logger.info("verCandados[FIN] retorno de registros vigentes: {}", r2);
                    return RespGenerica.of(r2);

                case VER_UNO:
                    CandadoTO candado = new CandadoTO();
                    candado.setNumero(numeroCandado);
                    candado.setUbicacion(ubicacion);
                    Respuesta<CandadoTO> r3 = FactoryBO.getCandadoBO().get(candado);

                    logger.info("verCandados[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
            }
        } catch(Exception e)
        {
            logger.error("verCandados[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verCandados[FIN] código de acción inválido: {}", accion);
        return null;
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

    @Path("candados/{numero}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarCandado(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("numero") Integer numeroCandado)
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
            Respuesta<List<UsoCandadoTO>> r1 = FactoryBO.getCandadoBO().getUsosCandado(Optional.of(Boolean.TRUE));
            logger.info("verUsosCandado[FIN] registros retornados: {}", r1);
            return RespGenerica.of(r1);
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
            @QueryParam("accion") Integer accion,
    		@QueryParam("codigo") String codigoEquipo)
    {
        logger.info ("verEquipos[INI] token: {}", tokenUbicacion);
        logger.info ("verEquipos[INI] accion: {}", accion);
        logger.info ("verEquipos[INI] codigoEquipo: {}", codigoEquipo);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verEquipos[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().get();
            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<EquipoTO>> r1 = FactoryBO.getEquipoBO().getTodos(ubicacion,Optional.empty());
                    logger.info("verEquipos[FIN] retorno de todos los registros: {}", r1);
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<EquipoTO>> r2 = FactoryBO.getEquipoBO().getVigentes(ubicacion);
                    logger.info("verEquipos[FIN] retorno de registros vigentes: {}", r2);
                    return RespGenerica.of(r2);

                case VER_UNO:
                    EquipoTO equipo = new EquipoTO();
                    equipo.setCodigo(codigoEquipo);
                    equipo.setUbicacion(ubicacion);
                    Respuesta<EquipoTagsTO> r3 = FactoryBO.getEquipoBO().get(equipo);
                    logger.info("verEquipos[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
            }
        } catch(Exception e)
        {
            logger.error("verEquipos[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verEquipos[FIN] código de acción inválido: {}", accion);
        return null;
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

    @Path("equipos/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("codigo") String codigoEquipo)
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

    @Path("equipos/{idEquipo}/tags")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verTags(
    		@HeaderParam("token") String tokenUbicacion,
            @PathParam("idEquipo") Integer idEquipo,
    		@QueryParam("accion") Integer accion,
    		@QueryParam("numero") Integer numeroTag)
    {
        logger.info ("verTags[INI] token: {}" , tokenUbicacion);
        logger.info ("verTags[INI] idEquipo: {}", idEquipo);
        logger.info ("verTags[INI] accion: {}", accion);
        logger.info ("verTags[INI] numeroTag: {}", numeroTag);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verTags[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            EquipoTO pkEquipo = new EquipoTO();
            pkEquipo.setUbicacion(ubicacion);
            pkEquipo.setId(idEquipo);

            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<TagTO>> r1 = FactoryBO.getEquipoBO().getTagsTodos(pkEquipo,Optional.empty());
                    logger.info("verTags[FIN] retorno de todos los registros: {}", r1);
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<TagTO>> r2 = FactoryBO.getEquipoBO().getTagsVigentes(pkEquipo);
                    logger.info("verTags[FIN] retorno de registros vigentes: {}", r2);
                    return RespGenerica.of(r2);

                case VER_UNO:
                    TagTO tag = new TagTO();
                    tag.setEquipo(pkEquipo);
                    tag.setNumero(numeroTag);
                    Respuesta<TagTO> r3 = FactoryBO.getEquipoBO().getTag(tag);
                    logger.info("verTags[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
            }
        } catch(Exception e)
        {
            logger.error("verTags[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verTags[FIN] código de acción inválido: {}", accion);
        return null;
    }

    @Path("empresas")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verEmpresas(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("accion") Integer accion,
    		@QueryParam("rut") String rutEmpresa)
    {
        logger.info ("verEmpresa[INI] token: {}", tokenUbicacion);
        logger.info ("verEmpresa[INI] accion: {}", accion);
        logger.info ("verEmpresa[INI] rut: {}", rutEmpresa);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verEmpresa[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<EmpresaTO>> r1 = FactoryBO.getEmpresaBO().getTodos(null);
                    logger.info("verEmpresa[FIN] retorno de todos los registros: {}", r1.getResultado());
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<EmpresaTO>> r2 = FactoryBO.getEmpresaBO().getVigentes();
                    logger.info("verEmpresa[FIN] retorno de registros vigentes: {}", r2.getResultado());
                    return RespGenerica.of(r2);

                case VER_UNO:
                    EmpresaTO empresa = new EmpresaTO();
                    empresa.setRut( Rut.valueOf(rutEmpresa) );
                    Respuesta<EmpresaTO> r3 = FactoryBO.getEmpresaBO().get(empresa);

                    logger.info("verEmpresa[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
            }
        } catch(Exception e)
        {
            logger.error("verEmpresa[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verEmpresa[FIN] código de acción inválido: {}", accion);
        return null;
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

    @Path("empresas/{rut}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarEmpresa(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("rut") String rutEmpresa)
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

    @Path("trabajadores/{rut}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarTrabajador(
    		@HeaderParam("token") String tokenUbicacion,
    		@PathParam("rut") String rutTrabajador)
    {
        logger.info ("eliminarTrabajador[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarTrabajador[INI] rutEmpresa: {}", rutTrabajador);

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
    	logger.info ("guardarTrabajador[INI] empresa: {}", jsonTrabajador);
        
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
    		@QueryParam("accion") Integer accion,
    		@QueryParam("rut") String rutTrabajador)
    {
        logger.info ("verTrabajadores[INI] token: {}", tokenUbicacion);
        logger.info ("verTrabajadores[INI] accion: {}", accion);
        logger.info ("verTrabajadores[INI] rutTrabajador: {}", rutTrabajador);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verTrabajadores[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<TrabajadorTO>> r1 = FactoryBO.getTrabajadorBO().getTodos(null);
                    logger.info("verTrabajadores[FIN] retorno de todos los registros: {}", r1.getResultado());
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<TrabajadorTO>> r2 = FactoryBO.getTrabajadorBO().getVigentes();
                    logger.info("verTrabajadores[FIN] retorno de registros vigentes: {}", r2.getResultado());
                    return RespGenerica.of(r2);

                case VER_UNO:
                    TrabajadorTO trabajador = new TrabajadorTO();
                    trabajador.setRut( Rut.valueOf(rutTrabajador) );
                    Respuesta<TrabajadorTO> r3 = FactoryBO.getTrabajadorBO().get(trabajador);

                    logger.info("verTrabajadores[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
            }
        } catch(Exception e)
        {
            logger.error("verTrabajadores[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verTrabajadores[FIN] código de acción inválido: {}", accion);
        return null;
    }

    
    @Path("funcionesBloqueo")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verFuncionesBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("accion") Integer accion)
    {
        logger.info ("verFuncionesBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("verFuncionesBloqueo[INI] accion: {}", accion);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verFuncionesBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<FuncionBloqueoTO>> r1 = FactoryBO.getUbicacionBO().getFunciones(Optional.empty());
                    logger.info("verFuncionesBloqueo[FIN] retorno de todos los registros: {}", r1.getResultado());
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<FuncionBloqueoTO>> r2 = FactoryBO.getUbicacionBO().getFunciones(Optional.of(Boolean.TRUE));
                    logger.info("verFuncionesBloqueo[FIN] retorno de registros vigentes: {}", r2.getResultado());
                    return RespGenerica.of(r2);
            }
        } catch(Exception e)
        {
            logger.error("verFuncionesBloqueo[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verFuncionesBloqueo[FIN] código de acción inválido: {}", accion);
        return null;
    }
}
