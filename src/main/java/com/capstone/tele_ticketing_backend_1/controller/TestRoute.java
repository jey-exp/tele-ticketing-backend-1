package com.capstone.tele_ticketing_backend_1.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1")
public class TestRoute {
    @GetMapping("/greeti")
    private String geetti(){
        return "helo man";
    }
}
