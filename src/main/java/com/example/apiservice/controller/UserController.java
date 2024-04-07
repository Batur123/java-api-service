package com.example.apiservice.controller;

import com.example.apiservice.annotation.RateLimited;
import com.example.apiservice.model.UsersModel.GetAllUsersObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RateLimited(limit = 10, windowInSeconds = 60) // Example rate limit: 10 requests per 60 seconds
    @GetMapping("/test")
    public GetAllUsersObject getAllUsers() {
        GetAllUsersObject responseObject = new GetAllUsersObject();
        responseObject.setMessage("Hello, World!");
        return responseObject;
    }
}
