package com.example.demo5;

import com.example.demo5.service.GreetingService;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HelloResourceTest {


    @Mock
    GreetingService greetingService;

    Dispatcher dispatcher;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        var hello = new HelloResource(greetingService);
        dispatcher.getRegistry().addSingletonResource(hello);
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
