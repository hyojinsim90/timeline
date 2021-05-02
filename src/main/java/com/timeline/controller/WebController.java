package com.timeline.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SuppressWarnings("deprecation")
public class WebController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "/index.html";
    }

    public String getErrorPath() {
        return null;
    }

}