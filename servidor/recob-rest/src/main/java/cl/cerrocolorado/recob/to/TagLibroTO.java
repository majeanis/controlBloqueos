package cl.cerrocolorado.recob.to;

/**
 *
 * @author mauricio.camara
 */
public class TagLibroTO extends TagTO
{
    private LibroBloqueoTO libro;

    public LibroBloqueoTO getLibro()
    {
        return libro;
    }

    public void setLibro(LibroBloqueoTO libro)
    {
        this.libro = libro;
    }
    
    @Override
    public boolean isKeyBlank()
    {
        return super.isKeyBlank() || libro == null || libro.isKeyBlank();
    }
}
