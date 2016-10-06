package org.ssa.ironyard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/podcaster")
public class Home
{
    @RequestMapping(value = "/")
    public String home()
    {
        return "/index.html";
    }
    
    @RequestMapping(value = "/login")
    public String login()
    {
        return "/login.html";
    }
}