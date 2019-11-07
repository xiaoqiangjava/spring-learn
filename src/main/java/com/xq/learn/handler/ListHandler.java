package com.xq.learn.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * 类型处理器，将字符串xxx|xxx转换成list
 * @author xiaoqiang
 * @date 2019/11/8 1:30
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class ListHandler extends BaseTypeHandler<List<String>>
{
    private static final String DELIMITER = "|";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException
    {
        ps.setString(i, String.join(DELIMITER, parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException
    {
        String value = rs.getString(columnName);

        return str2List(value, DELIMITER);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException
    {
        String value = rs.getString(columnIndex);

        return str2List(value, DELIMITER);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
    {
        String value = cs.getString(columnIndex);

        return str2List(value, DELIMITER);
    }

    /**
     * 将特定字符分割的字符串转换成list
     * @param value 字符串
     * @param delimiter 分割符
     * @return list
     */
    private List<String> str2List(String value, String delimiter)
    {
        List<String> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(value, delimiter);
        while (tokenizer.hasMoreTokens())
        {
            list.add(tokenizer.nextToken());
        }
        return list;
    }
}
