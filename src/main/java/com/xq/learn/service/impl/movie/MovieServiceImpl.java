package com.xq.learn.service.impl.movie;

import com.xq.learn.dao.movie.MovieMapper;
import com.xq.learn.model.movie.Movie;
import com.xq.learn.service.movie.MovieService;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 电影业务层实现类，相关业务逻辑都在该类中实现
 * @author xiaoqiang
 * @date 2019/11/6 23:12
 */
@Service
public class MovieServiceImpl implements MovieService
{
    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Resource
    private MovieMapper movieMapper;

    @Override
    @Transactional
    public List<Movie> list(String name)
    {
        logger.info("List movies.");
        movieMapper.list();
        return movieMapper.list(1016);
    }
}
