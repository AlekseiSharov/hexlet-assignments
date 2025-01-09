package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Qualifier("getTime")
    @Autowired
    private Daytime daytime;

    @GetMapping
    public String welcome() {
        return "It is "+ daytime.getName() +" now! Welcome to Spring!";
    }
}
