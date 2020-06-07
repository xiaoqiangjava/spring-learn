package com.xq.learn.controller.movie;

import com.fasterxml.jackson.annotation.JsonView;
import com.xq.learn.dao.movie.MovieMapper;
import com.xq.learn.message.MessageHelper;
import com.xq.learn.model.movie.Movie;
import com.xq.learn.model.response.CommonResponse;
import com.xq.learn.model.view.View;
import com.xq.learn.service.movie.MovieService;
import com.xq.learn.util.SpringBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(tags = "电影相关api")
public class MovieController
{
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MessageHelper messageHelper;

    @RequestMapping
    public String suffixMatch()
    {
        String message = messageHelper.getMessage("gasc.1001");
        logger.info("message: {}", message);
        logger.info("coming in");
        return "success";
    }

    @ApiOperation("查询电影列表")
    @JsonView(View.Movie.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "X-Auth-Token", value = "用户token信息", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "header", name = "Content-Type", value = "Media type", defaultValue = MediaType.APPLICATION_JSON_VALUE, required = true, dataType = "String")})
    @RequestMapping(value = "movies", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse list()
    {
        List<Movie> movies = movieService.list("name");
        MovieMapper movieMapper = SpringBeanUtil.getBean("movieMapper");
        logger.info("Succeed to list movies.");
        CommonResponse response = new CommonResponse();
        response.setIsSuccess(true);
        response.setMovies(movies);
        return response;
    }

    @RequestMapping(value = "movies", method = RequestMethod.POST)
    public CommonResponse save(@RequestBody Movie movie)
    {
        messageHelper.valid(movie);
        CommonResponse response = new CommonResponse();
        response.setIsSuccess(true);
        response.setMessage("Succeed to save movie.");
        return response;
    }
}
