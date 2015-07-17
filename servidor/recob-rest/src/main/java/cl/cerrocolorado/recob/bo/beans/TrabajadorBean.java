package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.bo.TrabajadorBO;
import cl.cerrocolorado.recob.po.TrabajadorPO;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Rut;
import cl.cerrocolorado.recob.utils.Transaccional;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mauricio.camara
 */
public class TrabajadorBean implements TrabajadorBO
{
    private static final Logger logger = LogManager.getLogger(TrabajadorBean.class);
    
    @Autowired
    private TrabajadorPO trabajadorPO;
    
    @Override
    @Transaccional
    public Respuesta<TrabajadorTO> guardar(TrabajadorTO trabajador) throws Exception
    {
        logger.info ("guardar[INI] trabajador: {}", trabajador);
        
        Resultado rtdo = new ResultadoProceso();
        
        if( trabajador == null)
        {
            rtdo.addError(this.getClass(), "Debe informar datos del trabajador");
            logger.info ("guardar[FIN] objeto trabajador llegó en null" );
            return Respuesta.of(rtdo);
        }
        
        if(Rut.isBlank(trabajador.getRut()))
        {
            rtdo.addError(this.getClass(), "Debe informar R.U.T. del trabajador" );
        }
        
        if( StringUtils.isBlank(trabajador.getCargo()))
        {
            rtdo.addError(this.getClass(), "Debe informar el cargo del trabajador" );
        }
        
        if( StringUtils.isBlank(trabajador.getNombre()))
        {
            rtdo.addError(this.getClass(), "Debe informar el nombre del trabajador" );
        }

        if( trabajador.getTieneCursoBloqueo()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar si el trabajador tuvo curso del bloqueo" );
        }

        if( trabajador.getVigente()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar la vigencia del trabajador" );
        }

        if( trabajador.getEmpresa() == null || Rut.isBlank( trabajador.getEmpresa().getRut() ) )
        {
            rtdo.addError(this.getClass(), "Debe informar el R.U.T. de la Empresa");
        }

        if(!rtdo.esExitoso())
        {
            logger.info("guardar[FIN] se detectaron errores de validación: {}", trabajador);
            return Respuesta.of(rtdo);
        }
        
        TrabajadorTO actual = trabajadorPO.getVigente(trabajador);
        if(actual != null)
        {
            trabajador.setId(actual.getId());

            // Si ya existe registro del trabajador y no hay cambio de
            // empresa, entonces procedemos a actualizar su registro
            if(trabajador.getEmpresa().getRut().equals(actual.getEmpresa().getRut()))
            {
                trabajador.getEmpresa().setId(actual.getEmpresa().getId());
                trabajadorPO.guardar(trabajador);
                logger.info("guardar[FIN] registro del trabajador actualizado con éxito: {}", trabajador);
                return Respuesta.of(rtdo,trabajador);
            } else if( trabajador.getVigente() && actual.getVigente() ) 
            {
                actual.setVigente(Boolean.FALSE);
                trabajadorPO.guardar(actual);
                trabajadorPO.guardar(trabajador);
                logger.info("guardar[FIN] se desactivo registro actual y se activo una nueva relación: actual {} nuevo {}", actual, trabajador);                
                return Respuesta.of(rtdo,trabajador);
            }
        }

        // Si se llega a este punto, entonces solor esta guardar el registro del trabajador
        trabajadorPO.guardar(trabajador);
        logger.info("guardar[FIN] trabajador guardado con éxito: {}", trabajador);
        return Respuesta.of(rtdo,trabajador);
    }

    @Override
    public Resultado eliminar(TrabajadorTO pkTrabajador) throws Exception
    {
        logger.info("eliminar[INI] pkTrabajador: {}", pkTrabajador);

        Resultado rtdo = new ResultadoProceso();
        
        if(pkTrabajador == null || pkTrabajador.getRut() == null)
        {
            rtdo.addError(this.getClass(), "Debe informar al R.U.T. del trabajador");
            logger.info("eliminar[FIN] pkTrabajador llegó en NULL");
            return rtdo;
        }

        // Si no se informa la Empresa entonces se elimina la relación que esté vigente
        TrabajadorTO eliminar;
        if( pkTrabajador.getEmpresa() == null || Rut.isBlank(pkTrabajador.getEmpresa().getRut()))
        {
            eliminar = trabajadorPO.getVigente(pkTrabajador);
            logger.debug("eliminar[001] después de buscar al registro actualmente vigente: {}", eliminar);
        } else
        {
            eliminar = trabajadorPO.get(pkTrabajador);
            logger.debug("eliminar[001] después de buscar al registro asociado a la empresa: {}", eliminar);            
        }

        if(eliminar==null)
        {
            rtdo.addError(this.getClass(), "No se encontró registro del Trabajador" );
            logger.info("eliminar[FIN] no se encontró registro eliminable");
            return rtdo;
        }
        
        if(!trabajadorPO.esEliminable(eliminar))
        {
            rtdo.addError(this.getClass(), "Caja de Bloqueo tiene registros asociados" );
            logger.info ("eliminar[FIN] registro no puede ser eliminado");
            return rtdo;
        }

        // Si llegamos a este punto, es posible eliminar al trabajador
        trabajadorPO.eliminar(eliminar);
        rtdo.addMensaje(this.getClass(), "Trabajador con R.U.T. #{1} eliminado con éxito", eliminar.getRut().toText() );

        logger.info("eliminar[FIN] registro eliminado con éxito");
        return rtdo;
    }

    @Override
    public Respuesta<TrabajadorTO> get(TrabajadorTO pkTrabajador)
    {
        logger.info( "get[INI] pkTrabajador: {}", pkTrabajador);

        Resultado rtdo = new ResultadoProceso();
        if(pkTrabajador==null || pkTrabajador.getRut()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar el R.U.T. del trabajador");
            logger.info ("get[FIN] no se informaron todos los filtros: {}", pkTrabajador);
            return Respuesta.of(rtdo);
        }
        
        TrabajadorTO trabajador;
        if( pkTrabajador.getEmpresa() == null || Rut.isBlank(pkTrabajador.getEmpresa().getRut()))
        {
            trabajador = trabajadorPO.getVigente(pkTrabajador);
            logger.debug("get[001] después de buscar al registro actualmente vigente: {}", trabajador);
        } else
        {
            trabajador = trabajadorPO.get(pkTrabajador);
            logger.debug("get[002] después de buscar al registro asociado a la empresa: {}", trabajador);            
        }

        logger.info ("get[FIN] registro retornado: {}", trabajador);
        return Respuesta.of(trabajador);
    }

    @Override
    public List<TrabajadorTO> getVigentes()
    {
        logger.info("getVigentes[INI]");
        
        List<TrabajadorTO> lista = trabajadorPO.getList(Boolean.TRUE);
        
        logger.info("getVigentes[FIN] cantidad de registros retornados: {}", lista.size());
        return lista;
    }

    @Override
    public List<TrabajadorTO> getVigentes(EmpresaTO pkEmpresa)
    {
        logger.info("getVigentes[INI] pkEmpresa: {}", pkEmpresa);
        
        List<TrabajadorTO> lista = trabajadorPO.getList(pkEmpresa, Boolean.TRUE);
        
        logger.info("getVigentes[FIN] cantidad de registros retornados: {}", lista.size());
        return lista;
    }

    @Override
    public List<TrabajadorTO> getTodos()
    {
        logger.info("getTodos[INI]");
        
        List<TrabajadorTO> lista = trabajadorPO.getList(null);
        
        logger.info("getTodos[FIN] cantidad de registros retornados: {}", lista.size());
        return lista;
    }

    @Override
    public List<TrabajadorTO> getTodos(EmpresaTO pkEmpresa)
    {
        logger.info("getTodos[INI] pkEmpresa: {}", pkEmpresa);
        
        List<TrabajadorTO> lista = trabajadorPO.getList(pkEmpresa, null);
        
        logger.info("getTodos[FIN] cantidad de registros retornados: {}", lista.size());
        return lista;
    }
}
