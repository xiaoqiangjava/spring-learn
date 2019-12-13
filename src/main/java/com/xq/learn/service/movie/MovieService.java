package com.xq.learn.service.movie;

import com.xq.learn.model.movie.Movie;
import java.util.List;

/**
 * 电影相关业务层接口
 * @author xiaoqiang
 * @date 2019/11/6 23:06
 */
public interface MovieService
{
    /**
     * 查询电影列表信息
     * @return movies 电影列表信息
     */
    List<Movie> list(String name);
}
