package com.xq.learn.service.impl.movie;

import com.xq.learn.dao.movie.MovieMapper;
import com.xq.learn.model.movie.Movie;
import com.xq.learn.service.movie.MovieService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 电影业务层实现类，相关业务逻辑都在该类中实现
 * @author xiaoqiang
 * @date 2019/11/6 23:12
 */
@Service
public class MovieServiceImpl implements MovieService
{
    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<Movie> list()
    {
        logger.info("List movies.");
        movieMapper.list();
        return movieMapper.list(1016);
    }
}
