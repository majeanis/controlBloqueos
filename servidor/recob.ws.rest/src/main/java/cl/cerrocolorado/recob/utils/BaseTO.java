package cl.cerrocolorado.recob.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class BaseTO implements ObjetoTO
{
    private static final long serialVersionUID = 1L;

    @Override
    public String toString()
    {
        return ToStringUtils.toString(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
