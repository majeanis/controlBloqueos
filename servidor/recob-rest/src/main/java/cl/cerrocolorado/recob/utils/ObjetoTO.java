package cl.cerrocolorado.recob.utils;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;

public interface ObjetoTO extends Serializable, Comparable<ObjetoTO>, Cloneable
{
    @Override
    public String toString();

    @Override
    public boolean equals(Object obj);

    @Override
    public default int compareTo(ObjetoTO obj)
    {
        return CompareToBuilder.reflectionCompare(this,obj);
    }
}
