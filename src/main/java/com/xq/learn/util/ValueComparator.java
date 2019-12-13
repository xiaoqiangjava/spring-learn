package com.xq.learn.util;

import java.util.Comparator;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/11/25 0:31
 */
public class ValueComparator implements Comparator<String>
{
    private String attr;
    private Map<String, Map<String, Object>> base;

    public ValueComparator(String attr, Map<String, Map<String, Object>> base)
    {
        this.attr = attr;
        this.base = base;
    }

    @Override
    public int compare(String o1, String o2)
    {
        int order = Double.valueOf(base.get(o2).get(attr).toString()).compareTo(Double.valueOf(base.get(o1).get(attr).toString()));
        return 0 == order ? o1.compareTo(o2) : order;
    }
}
