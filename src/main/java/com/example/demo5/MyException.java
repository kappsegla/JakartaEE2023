package com.example.demo5;

import com.example.demo5.interceptor.LoggingInterceptor;
import jakarta.ws.rs.WebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyException extends WebApplicationException {
    public MyException() {
        super();
    }
    public MyException(String message) {
        super("MyException Error: " +message);
    }
}
