package org.thepoet.brokage.controller;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test-auth")
    public String test() {
        return "You are authenticated!";
    }

    @GetMapping("/test-auth2")
    public String test2() {
        return "You are authenticated again!";
    }

    @GetMapping("/test-unauth")
    public String testUnauth() {
        return "You are not authenticated!";
    }
}
