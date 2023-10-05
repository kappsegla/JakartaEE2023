package com.example.demo5.service;

import com.example.demo5.interceptor.Logging;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Logging
public class HelloGreetingService implements GreetingService {

    @Override
    public String greeting() {
        return "Hello World";
    }
}
