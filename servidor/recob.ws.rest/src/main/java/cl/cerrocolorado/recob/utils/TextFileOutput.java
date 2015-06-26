package cl.cerrocolorado.recob.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TextFileOutput extends BufferedWriter
{
    private static final String defaultCharset = "UTF-8";

    public TextFileOutput(String pathArchivo) 
            throws IOException, FileNotFoundException
    {
        super(new OutputStreamWriter(new FileOutputStream(pathArchivo), defaultCharset));
    }

    public TextFileOutput(File archivo) 
            throws IOException, FileNotFoundException
    {
        super(new OutputStreamWriter(new FileOutputStream(archivo), defaultCharset));
    }
}
