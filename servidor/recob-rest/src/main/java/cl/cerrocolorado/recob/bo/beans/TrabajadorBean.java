package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.bo.TrabajadorBO;
import cl.cerrocolorado.recob.po.EmpresaPO;
import cl.cerrocolorado.recob.po.TrabajadorPO;
import cl.cerrocolorado.recob.to.entidades.EmpresaTO;
import cl.cerrocolorado.recob.to.entidades.TrabajadorTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Rut;
import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.mensajes.RegistrosQueryInfo;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
@Service("trabajadorBO")
public class TrabajadorBean implements TrabajadorBO
{
    private static final Logger logger = LogManager.getLogger(TrabajadorBean.class);
    
    @Autowired
    private TrabajadorPO trabajadorPO;

    @Autowired
    private EmpresaPO empresaPO;
    
    @Override
    @Transaccional
    public Respuesta<TrabajadorTO> save(TrabajadorTO trabajador)
    {
        logger.info ("save[INI] trabajador: {}", trabajador);
        
        Resultado rtdo = new ResultadoProceso();
        
        if( trabajador == null)
        {
            rtdo.addError(this.getClass(), "Debe informar datos del trabajador");
            logger.info ("save[FIN] objeto trabajador llegó en null" );
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
        } else
        {
            EmpresaTO empresa = empresaPO.obtener(trabajador.getEmpresa());
            if( empresa == null )
            {
                rtdo.addError(this.getClass(), "No existe empresa con R.U.T. %s", trabajador.getEmpresa().getRut().toText() );
            } else
            {
                trabajador.setEmpresa(empresa);
            }
        }

        logger.debug("save[001] es un nuevo registro: {}", trabajador.isIdBlank());
        TrabajadorTO otro = trabajadorPO.obtener(trabajador);
        if( trabajador.isIdBlank() )
        {
            if(otro != null)
            {
                rtdo.addError(this.getClass(), "Ya existe relación entre Trabajador [RUT: %s] y Empresa [RUT: %s]", trabajador.getRut().toText(), trabajador.getEmpresa().getRut().toText() );
            }
        } else if ( otro == null )
        {
            rtdo.addError(this.getClass(), "No existe relación entre Trabajador [RUT: %s] y Empresa [RUT: %s]", trabajador.getRut().toText(), trabajador.getEmpresa().getRut().toText() );    
        }

        logger.debug("save[002] resultado validaciones: {}", rtdo );
        if(!rtdo.esExitoso())
        {
            logger.info("save[FIN] se detectaron errores de validación: {}", rtdo);
            return Respuesta.of(rtdo);
        }

        // Si el trabajador ya tiene relación con otra empresa y ahora se está asignando
        // una relación con otra empresa,  entonces  es necesario marcar como no vigente
        // la relación actual.
        TrabajadorTO actual = trabajadorPO.getVigente(trabajador);
        if( actual != null && 
            trabajador.getVigente() && 
            !Objects.equals(trabajador.getEmpresa().getId(), actual.getEmpresa().getId()))
        {
            actual.setVigente(Boolean.FALSE);
            trabajadorPO.guardar(actual);
        }

        trabajadorPO.guardar(trabajador);
        rtdo.addMensaje(this.getClass(), "Trabajador guardado con éxito");

        logger.info("save[FIN] trabajador guardado con éxito: {}", trabajador);
        return Respuesta.of(rtdo,trabajador);
    }

    @Override
    public Respuesta<TrabajadorTO> delete(TrabajadorTO pkTrabajador) throws Exception
    {
        logger.info("delete[INI] pkTrabajador: {}", pkTrabajador);

        Resultado rtdo = new ResultadoProceso();
        
        if(pkTrabajador == null || pkTrabajador.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar al R.U.T. del trabajador");
            logger.info("delete[FIN] pkTrabajador llegó en NULL");
            return Respuesta.of(rtdo);
        }

        // Si no se informa la Empresa entonces se elimina la relación que esté vigente
        TrabajadorTO trabajador;
        if( pkTrabajador.getEmpresa() == null || Rut.isBlank(pkTrabajador.getEmpresa().getRut()))
        {
            trabajador = trabajadorPO.getVigente(pkTrabajador);
            logger.debug("delete[001] después de buscar al registro actualmente vigente: {}", trabajador);
        } else
        {
            trabajador = trabajadorPO.obtener(pkTrabajador);
            logger.debug("delete[001] después de buscar al registro asociado a la empresa: {}", trabajador);            
        }

        if(trabajador==null)
        {
            rtdo.addError(this.getClass(), "No se encontró registro del Trabajador" );
            logger.info("delete[FIN] no se encontró registro eliminable");
            return Respuesta.of(rtdo);
        }
        
        if(!trabajadorPO.esEliminable(trabajador))
        {
            rtdo.addError(this.getClass(), "Caja de Bloqueo tiene registros asociados" );
            logger.info ("delete[FIN] registro no puede ser eliminado");
            return Respuesta.of(rtdo);
        }

        // Si llegamos a este punto, es posible eliminar al trabajador
        trabajadorPO.eliminar(trabajador);
        rtdo.addMensaje(this.getClass(), "Trabajador con R.U.T. %s eliminado con éxito", trabajador.getRut().toText() );

        logger.info("delete[FIN] registro eliminado con éxito");
        return Respuesta.of(rtdo,trabajador);
    }

    @Override
    public Respuesta<TrabajadorTO> get(TrabajadorTO pkTrabajador)
    {
        logger.info( "get[INI] pkTrabajador: {}", pkTrabajador);

        Resultado rtdo = new ResultadoProceso();
        if(pkTrabajador==null || pkTrabajador.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar el R.U.T. del trabajador");
            logger.info ("get[FIN] no se informaron todos los filtros: {}", pkTrabajador);
            return Respuesta.of(rtdo);
        }
        
        TrabajadorTO trabajador;
        if( pkTrabajador.getEmpresa()==null || pkTrabajador.getEmpresa().isKeyBlank())
        {
            trabajador = trabajadorPO.getVigente(pkTrabajador);
            logger.debug("get[001] después de buscar al registro actualmente vigente: {}", trabajador);
        } else
        {
            trabajador = trabajadorPO.obtener(pkTrabajador);
            logger.debug("get[002] después de buscar al registro asociado a la empresa: {}", trabajador);            
        }

        logger.info ("get[FIN] registro retornado: {}", trabajador);
        return Respuesta.of(trabajador);
    }

    @Override
    public Respuesta<List<TrabajadorTO>> getTrabajadores(Optional<Boolean> vigencia)
    {
        logger.info("getTrabajadores[INI] vigencia: {}", vigencia);

        Resultado rtdo = new ResultadoProceso();
        List<TrabajadorTO> lista = trabajadorPO.getList(vigencia);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));
        
        logger.info("getTrabajadores[FIN] cantidad de registros retornados: {}", lista.size());
        return Respuesta.of(rtdo, lista);
    }

    @Override
    public Respuesta<List<TrabajadorTO>> getTrabajadores(EmpresaTO pkEmpresa,
                                                         Optional<Boolean> vigencia)
    {
        logger.info("getTrabajadores[INI] pkEmpresa: {}", pkEmpresa);
        logger.info("getTrabajadores[INI] vigencia: {}", vigencia);

        Resultado rtdo = new ResultadoProceso();
        if( pkEmpresa == null || pkEmpresa.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar la Empresa" );
            logger.info("getTrabajadores[FIN] no se informaron todos los filtros: {}", pkEmpresa);
            return Respuesta.of(rtdo);
        }
        
        List<TrabajadorTO> lista = trabajadorPO.getList(pkEmpresa, Optional.empty());
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTrabajadores[FIN] cantidad de registros retornados: {}", lista.size());
        return Respuesta.of(rtdo, lista);
    }
}
