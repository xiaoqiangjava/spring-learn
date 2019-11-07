package com.xq.learn.dao.movie;

import com.xq.learn.model.movie.Movie;
import java.util.List;

/**
 * Movie相关Mapper接口
 * @author xiaoqiang
 * @date 2019/11/6 22:55
 */
public interface MovieMapper
{
    /**
     * 查询电影列表信息
     * @return movies 电影列表信息
     */
    List<Movie> list();

    /**
     * 根据id查询电影信息
     * @return movies 电影列表信息
     */
    List<Movie> list(int mid);
}
