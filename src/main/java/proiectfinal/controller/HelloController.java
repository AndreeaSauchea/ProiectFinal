package proiectfinal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @RequestMapping(method = RequestMethod.GET, value = {"/"})
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
