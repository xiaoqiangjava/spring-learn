package com.xiaoqiang.learn.springboot.demo.exception.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/4/22 23:09
 */
@RestController
@RequestMapping("/v1/demo")
public class DemoController
{
    Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getUser(HttpServletRequest request, @RequestParam String name)
    {
        if (name.equals("xiaoqiang"))
        {
            throw new RuntimeException("");
        }
        return name;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> addUser(HttpServletRequest request, @RequestBody Map<String, String> body) throws Exception
    {
        String name = body.get("name");
        if (null == name)
        {
            throw new Exception("exception");
        }
        return body;
    }
}
