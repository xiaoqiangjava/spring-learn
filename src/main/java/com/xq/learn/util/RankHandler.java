package com.xq.learn.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author xiaoqiang
 * @date 2019/11/24 23:29
 */
public class RankHandler
{
    public List<Candidate> doRank(Set<String> ids, List<AttrWeight> attrWeights, Map<String, Map<String, Object>> profiles)
    {
        attrWeights.forEach(attrWeight ->
        {
            String attr = attrWeight.getName();
            double weight = attrWeight.getWeight();
            profiles.values().forEach(profile -> profile.computeIfAbsent(attr, key -> 0.0));
            ValueComparator comparator = new ValueComparator(attr, profiles);
            Map<String, Map<String, Object>> sort = new TreeMap<>(comparator);
            sort.putAll(profiles);
        });

        return null;
    }

    public static void main(String[] args)
    {
        Map<String, Map<String, Object>> profiles = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();
        profile.put("price", 12.34);
        profile.put("sale", 123);

        Map<String, Object> profile1 = new HashMap<>();
        profile1.put("prices", 15.34);
        profile1.put("sale", 103);

        Map<String, Object> profile2 = new HashMap<>();
        profile2.put("price", 15.88);
        profile2.put("sale", 103);

        profiles.put("1", profile);
        profiles.put("2", profile1);
        profiles.put("3", profile2);

        System.out.println("处理之前：" + profiles);

        String attr = "price";

        profiles.values().forEach(pro -> pro.computeIfAbsent(attr, key -> 0));
        System.out.println("处理之后：" + profiles);
        ValueComparator comparator = new ValueComparator(attr, profiles);
        Map<String, Map<String, Object>> sort = new TreeMap<>(comparator);
        sort.putAll(profiles);
        System.out.println("price sort：" + sort);
        System.out.println("key: " + sort.keySet());

        String attr1 = "sale";
        comparator = new ValueComparator(attr1, profiles);
        sort = new TreeMap<>(comparator);
        sort.putAll(profiles);
        System.out.println("sale sort：" + sort);
        System.out.println("key: " + sort.keySet());

    }
}
