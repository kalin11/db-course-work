package edu.tinkoff.tinkoffbackendacademypetproject.pageControllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {
    @RequestMapping(value = "/error")
    public String handleError() {
        return "/index.html";
    }

}
