package com.xq.learn.model.view;

/**
 * @author xiaoqiang
 * @date 2019/12/27 21:15
 */
public interface View
{
    public interface BasicView {}
    public interface Movie extends BasicView {}

    public interface User extends BasicView {}
}
