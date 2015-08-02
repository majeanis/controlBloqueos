package cl.cerrocolorado.recob.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class EntidadTO extends BaseTO
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Determina si los campos que componen a la llave de negocio están en blanco o no, incluyendo NULLs
     * @return 
     */
    @JsonIgnore
    public abstract boolean isKeyBlank();

    /**
     * Determina si el campo Id está en NULL o en Cero
     * @return 
     */
    @JsonIgnore
    public abstract boolean isIdBlank();
}
