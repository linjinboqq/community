package com.nowcoder.community.controller;

import com.nowcoder.community.dao.Aldao;
import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/al")
public class Al {

    @Autowired
    AlphaDao aldao;


    @RequestMapping("/student/{id}")
    @ResponseBody
    public String hello(@PathVariable("id") int id) {
        return id + " ";
    }
}
