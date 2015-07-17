package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.bo.EquipoBO;
import cl.cerrocolorado.recob.po.CajaBloqueoPO;
import cl.cerrocolorado.recob.po.EquipoPO;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Transaccional;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mauricio.camara
 */
public class EquipoBean implements EquipoBO
{
    private static final Logger logger = LogManager.getLogger(EquipoBean.class);

    @Autowired
    private EquipoPO equipoPO;

    private Resultado validarTag(TagTO tag, int index)
    {
        logger.info ("validarTag[INI] tag: {}", tag);
        logger.info ("validarTag[INI] index: {}", index);
        
        Resultado rtdo = new ResultadoProceso();
        
        if(tag == null)
        {
            rtdo.addError(this.getClass(), "Debe informar los datos del Tag [indice: #{1}]", index);
            logger.info("validarTag[FIN] objeto tag llegó en NULL");
            return rtdo;
        }
        if(tag.getEnergiaCero() == null)
        {
            rtdo.addError(this.getClass(), "Debe informar energía cero [indice: #{1}]", index );
        }
        if(tag.getVigente() == null)
        {
            rtdo.addError(this.getClass(), "Debe informar la vigencia del tag [indice: #{1}]", index );
        }
        if(StringUtils.isBlank(tag.getDescripcion()))
        {
            rtdo.addError(this.getClass(), "Debe informar la descripción del tag [indice: #{1}]", index );
        }
        if(StringUtils.isBlank(tag.getNombre()))
        {
            rtdo.addError(this.getClass(), "Debe informar el nombre del tag [indice: #{1}]", index );
        }
        if(tag.getNumero()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar el N° del tag [indice: #{1}]", index );
        }
        if(!rtdo.esExitoso())
        {
            logger.info ("validarTag[FIN] errores de validación: {} {}", rtdo, tag);
            return rtdo;
        }

        // Si es un TAG que aún no se ha creado, puesto que tampoco tiene el
        // Id del Equipo al cual pertenece, entonces no hay más validaciones
        if(tag.getIdEquipo() == null)
        {
            logger.info ("validarTag[FIN] tag pertenece a un equipo nuevo: {}", rtdo ); 
            return rtdo;
        }

        TagTO otro = equipoPO.getTag(tag);
        if( otro == null )
        {
            logger.info ("validarTag[FIN] no existe otro tag con el mismo número: {}", rtdo);
            return rtdo;
        }

        // Si existe un TAG con el número dado y no corresponde al mismo tag que estamos validando
        if( tag.getId() == null || !Objects.equals(tag.getId(), otro.getId()))
        {
            rtdo.addError(this.getClass(), "Ya existe TAG con el N° #{1} [id: #{2}[index: #{3}]", tag.getNumero(), otro.getId(), index);
            logger.info("validarTag[FIN] ya existe el N° de TAG: tag:{} - otro:{}", tag, otro);
            return rtdo;
        }

        logger.info ("validarTag[FIN] tag validado con exito: {}", tag);
        return rtdo;
    }
    
    @Override
    @Transaccional
    public Respuesta<EquipoTO> guardar(EquipoTO equipo) throws Exception
    {
       logger.info ("guardar[INI] equipo: {}", equipo);
       
       Resultado rtdo = ResultadoProceso();
       if(equipo==null)
       {
           rtdo.addError(this.getClass(), "Debe informar los datos del Equipo");
           logger.info ("guardar[FIN] se informó el objeto en NULL");
           return Respuesta.of(rtdo);
       }
       
       if(equipo.getVigente() == null)
       {
           rtdo.addError(this.getClass(), "Debe informar la vigencia del Equipo");
       }
       if(StringUtils.isBlank(equipo.getCodigo()))
       {
           rtdo.addError(this.getClass(),"Debe informar el código del equipo");
       }
       if(equipo.getUbicacion() == null || equipo.getUbicacion().getId() == null)
       {
           rtdo.addError(this.getClass(), "El equipo debe estar asociado a una ubicación" );
       }
       
       // Si contiene TAGs, entonces también los validamos
       if(equipo.getTags() != null)
       {
           for(int i=0; i < equipo.getTags().size(); i++ )
           {
               rtdo.append(validarTag(equipo.getTags().get(i),i));
           }
       }
       
       // Validadmos que no se repita el N° de TAGs, entre todos los de la lista
       for(int i=0; i < equipo.getTags().size(); i++)
       {
           TagTO tag = equipo.getTags().get(i);

           for( int j=0; j < equipo.getTags().size(); j++ )
           {
               TagTO otro = equipo.getTags().get(j);

               if( tag != otro && tag.getNumero() == otro.getNumero() )
               {
                   rtdo.addError(this.getClass(), "N° de TAG #{1} está repetido en la lista [indices: #{2}-#{3}", i, j );
                   logger.debug ("guardar[001] N° de TAG está repetido: tag {} otro {}", tag, otro);
               }
           }
       }
       
       if(!rtdo.esExitoso())
       {
           logger.info( "guardar[FIN] se encontraron errores de validación: {}", rtdo);
           return Respuesta.of(rtdo);
       }
       
       equipoPO.guardar(equipo);
       logger.info ("guardar[FIN] equipo guardado con éxito: {}", equipo);
       return Respuesta.of(equipo);
    }

    @Override
    public Resultado eliminar(EquipoTO pk) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Respuesta<EquipoTO> get(EquipoTO pk)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EquipoTO> getVigentes(UbicacionTO pkUbicacion)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EquipoTO> getTodos(UbicacionTO pkUbicacion)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TagTO> getTagsEnergiaCero(EquipoTO pk)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TagTO> getTagsVigentes(EquipoTO pk)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Resultado ResultadoProceso()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
