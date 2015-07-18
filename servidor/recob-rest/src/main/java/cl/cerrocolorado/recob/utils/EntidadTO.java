package cl.cerrocolorado.recob.utils;

public abstract class EntidadTO extends BaseTO
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Determina si los campos que componen a la llave de negocio están en blanco o no, incluyendo NULLs
     * @return 
     */
    public abstract boolean isKeyBlank();
}
