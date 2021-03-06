package com.xq.learn.model.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.xq.learn.model.view.View;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * movie实体类
 * @author xiaoqiang
 * @date 2019/11/6 23:07
 */
@JsonView(View.Movie.class)
@ApiModel
public class Movie implements Serializable
{
    @ApiModelProperty(value = "电影id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private int mid;

    @NotNull(message = "gasc.1001")
    private String name;

    @JsonProperty("time_long")
    private String timeLong;

    private String issue;

    private String shoot;

    private String language;

    private List<String> genres;

    private List<String> actors;

    private List<String> directors;

    public int getMid()
    {
        return mid;
    }

    public void setMid(int mid)
    {
        this.mid = mid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTimeLong()
    {
        return timeLong;
    }

    public void setTimeLong(String timeLong)
    {
        this.timeLong = timeLong;
    }

    public String getIssue()
    {
        return issue;
    }

    public void setIssue(String issue)
    {
        this.issue = issue;
    }

    public String getShoot()
    {
        return shoot;
    }

    public void setShoot(String shoot)
    {
        this.shoot = shoot;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    public List<String> getActors()
    {
        return actors;
    }

    public void setActors(List<String> actors)
    {
        this.actors = actors;
    }

    public List<String> getDirectors()
    {
        return directors;
    }

    public void setDirectors(List<String> directors)
    {
        this.directors = directors;
    }
}
