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
            return new Respuesta<>(rtdo);
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

        if( trabajador.getEmpresa() == null)
        {
            rtdo.addError(this.getClass(), "Debe informar la Empresa");
        } else if( Rut.isBlank( trabajador.getEmpresa().getRut() ) )
        {
            rtdo.addError(this.getClass(), "Debe informar el R.U.T. de la Empresa");
        }

        if(!rtdo.esExitoso())
        {
            logger.info("guardar[FIN] se detectaron errores de validación: {}", trabajador);
            return new Respuesta<>(rtdo);
        }
        
        TrabajadorTO actual = trabajadorPO.getVigente(trabajador.getRut());
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
                return new Respuesta<>(rtdo,trabajador);
            } else if( trabajador.getVigente() && actual.getVigente() ) 
            {
                actual.setVigente(Boolean.FALSE);
                trabajadorPO.guardar(actual);
                trabajadorPO.guardar(trabajador);
                logger.info("guardar[FIN] se desactivo registro actual y se activo una nueva relación: actual {} nuevo {}", actual, trabajador);                
                return new Respuesta<>(rtdo,trabajador);
            }
        }

        // Si se llega a este punto, entonces solor esta guardar el registro del trabajador
        trabajadorPO.guardar(trabajador);
        logger.info("guardar[FIN] trabajador guardado con éxito: {}", trabajador);
        return new Respuesta<>(rtdo,trabajador);
    }

    @Override
    public Resultado eliminar(TrabajadorTO pkTrabajador) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrabajadorTO get(TrabajadorTO pkTrabajador)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrabajadorTO> getVigentes()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrabajadorTO> getVigentes(EmpresaTO pkEmpresa)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrabajadorTO> getTodos()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrabajadorTO> getTodos(EmpresaTO pkEmpresa)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
