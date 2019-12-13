package com.xq.learn.controller.movie;

import com.xq.learn.dao.movie.MovieMapper;
import com.xq.learn.model.movie.Movie;
import com.xq.learn.service.movie.MovieService;
import com.xq.learn.util.SpringBeanUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Movie相关controller控制器，restful请求入口
 * @author xiaoqiang
 * @date 2019/11/6 22:59
 */
@RestController
@RequestMapping("/v1/learn")
public class MovieController
{
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "movies", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> list()
    {
        List<Movie> movies = movieService.list("name");
        MovieMapper movieMapper = SpringBeanUtil.getBean("movieMapper");
        logger.info("Succeed to list movies.");
        return movies;
    }
}
