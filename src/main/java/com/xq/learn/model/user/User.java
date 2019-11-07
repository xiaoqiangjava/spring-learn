package com.xq.learn.model.user;

/**
 * 用户实体类
 * @author xiaoqiang
 * @date 2019/11/6 23:55
 */
public class User
{
    private int uid;

    private String name;

    private int age;

    private String phone;

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}
