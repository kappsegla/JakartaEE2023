package com.example.demo5;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;

@ApplicationScoped
public class HelloGreetingService implements GreetingService{

    @Override
    public String greeting() {
        return "Hello World";
    }
}
