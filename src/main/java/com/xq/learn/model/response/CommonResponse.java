package com.xq.learn.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.xq.learn.model.movie.Movie;
import com.xq.learn.model.user.User;
import com.xq.learn.model.view.View;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author xiaoqiang
 * @date 2019/12/23 13:32
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse
{
    @JsonProperty(value = "is_success")
    @JsonView(View.BasicView.class)
    private boolean isSuccess;

    @JsonView(View.BasicView.class)
    @ApiModelProperty(hidden = true)
    private String message;

    @JsonView(View.Movie.class)
    private List<Movie> movies;

    @JsonView(View.User.class)
    private List<User> users;

    public boolean getIsSuccess()
    {
        return isSuccess;
    }

    public void setIsSuccess(boolean success)
    {
        isSuccess = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<Movie> getMovies()
    {
        return movies;
    }

    public void setMovies(List<Movie> movies)
    {
        this.movies = movies;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers(List<User> users)
    {
        this.users = users;
    }
}
