package edu.tinkoff.tinkoffbackendacademypetproject.pageControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping(value = {
            "/",
            "/courses",
            "/subjects**",
            "/subject-topics/**",
            "/publications/**",
            "/publications",
            "/users/**",
            "/feed/**",
            "/about/**"
    })
    public String reroute() {
        return "/index.html";
    }

}
