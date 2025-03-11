package com.prokopchuk.noteapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/is-alive")
    public String isAlive() {
        return "I am alive";
    }

}
