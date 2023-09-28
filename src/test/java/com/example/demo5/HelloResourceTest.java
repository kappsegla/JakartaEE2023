package com.example.demo5;

import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloResourceTest {

    Dispatcher dispatcher;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addPerRequestResource(HelloResource.class);
    }

    @Test
    public void testHello() throws Exception {
        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.get("/hello");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        // Assert the response status code and content
        assertEquals(200, response.getStatus());
        assertEquals("Hello, World!", response.getContentAsString());
    }

    @Test
    void testPersons() throws Exception {
        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.get("/hello/persons");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        // Assert the response status code and content
        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON,response.getOutputHeaders().get("Content-type").getFirst().toString());
        assertEquals("{\"name\":\"Kalle\",\"age\":40}", response.getContentAsString());
    }
}
