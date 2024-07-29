package com.hsenid.Paypro.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    // Endpoint to handle requests to the home page
    @GetMapping("/home")
    public String home() {
        // Returns a simple message for the home page
        return "This is the Home Page";
    }

    // Endpoint to handle requests to the products page
    @GetMapping("/products")
    public String products() {
        // Returns a simple message for the products page
        return "This is the Products Page";
    }

    // Endpoint to handle requests to the dashboard page
    @GetMapping("/dashboard")
    public String dashboard() {
        // Returns a simple message for the dashboard page
        return "This is the Dashboard Page";
    }

    // Endpoint to handle requests to the manage page
    @GetMapping("/manage")
    public String manage() {
        // Returns a simple message for the manage page
        return "This is the Manage Page";
    }
}
