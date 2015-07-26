package cl.cerrocolorado.recob.to;

/**
 *
 * @author mauricio.camara
 */
public class EnergiaLibroTO extends EnergiaTO
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
        return libro == null || libro.isKeyBlank() || super.isKeyBlank();
    }
}
