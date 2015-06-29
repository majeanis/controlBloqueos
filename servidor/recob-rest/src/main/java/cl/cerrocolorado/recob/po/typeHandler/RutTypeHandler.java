package cl.cerrocolorado.recob.po.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import cl.cerrocolorado.recob.utils.Rut;

/**
 * @author mauricio.camara
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(Rut.class)
public class RutTypeHandler extends BaseTypeHandler<Rut>
{
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Rut parameter, JdbcType jt) throws SQLException
    {
        ps.setString(i, parameter.toText() );
    }

    @Override
    public Rut getNullableResult(ResultSet rs, String columnName) throws SQLException
    {
        String rut = rs.getString(columnName);
        if(rut == null ) return null;

        return Rut.valueOf( rs.getString(columnName) );
    }

    @Override
    public Rut getNullableResult(ResultSet rs, int i) throws SQLException
    {
        String rut = rs.getString(i);
        if(rut == null ) return null;

        return Rut.valueOf( rs.getString(i) );
    }

    @Override
    public Rut getNullableResult(CallableStatement cs, int i) throws SQLException
    {
        String rut = cs.getString(i);
        if(rut == null ) return null;

        return Rut.valueOf( cs.getString(i) );
    }
}
