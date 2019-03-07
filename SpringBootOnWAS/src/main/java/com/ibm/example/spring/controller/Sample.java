package com.ibm.example.spring.controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Sample {

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex (@Valid @Pattern(regexp = "[0-9]+", message = "The id must be a valid number") @PathVariable String id)  {
        return "Hello world : " + id;
    }
}
