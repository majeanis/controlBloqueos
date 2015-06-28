package cl.cerrocolorado.recob.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextFileInput extends BufferedReader
{
    private static final String defaultCharset = "UTF-8";
    private static final int    sizeBuffer     = 1024 * 1024 * 5;

    public TextFileInput(String pathArchivo) 
            throws IOException, FileNotFoundException
    {
        super(new InputStreamReader(new FileInputStream(pathArchivo), defaultCharset));
    }

    public TextFileInput(File archivo) 
            throws IOException, FileNotFoundException
    {
        super(new InputStreamReader(new FileInputStream(archivo), defaultCharset));
    }

    public String readAll() 
            throws IOException
    {
        StringBuilder data = new StringBuilder();

        char[] buffer = new char[sizeBuffer];

        for (int count = read(buffer); count != -1;)
        {
            data.append(buffer, 0, count);
            count = read(buffer);
        }

        return (data.toString());
    }

    public static String readFile(File archivo) 
            throws FileNotFoundException, IOException
    {
        if (archivo == null)
            return "";

        TextFileInput reader = new TextFileInput(archivo);
        String data = reader.readAll();
        reader.close();

        return data;
    }
}
