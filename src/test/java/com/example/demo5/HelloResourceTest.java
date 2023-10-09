package com.example.demo5;

import com.example.demo5.service.GreetingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Providers;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        var helloResource = new HelloResource(greetingService);
        dispatcher.getRegistry().addSingletonResource(helloResource);
        // Create your custom ExceptionMapper
        ExceptionMapper<MyException> mapper = new MyExceptionMapper();
        // Register your custom ExceptionMapper
        dispatcher.getProviderFactory().registerProviderInstance(mapper);
    }

    @Test
    public void helloReturnsHelloWorldWithStatus200() throws Exception {
        //Teach mocked greetingService how to respond
        Mockito.when(greetingService.greeting()).thenReturn("Hello, World!");

        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.get("/hello");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        // Assert the response status code and content
        assertEquals(200, response.getStatus());
        assertEquals("Hello, World!", response.getContentAsString());
    }

    @Test
    void getPersonsReturnsOnePerson() throws Exception {
        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.get("/persons");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        // Assert the response status code and content
        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getOutputHeaders().get("Content-type").getFirst().toString());
        assertEquals("[{\"name\":\"Kalle\",\"age\":40}]", response.getContentAsString());
    }

    @Test
    void postPerson() throws Exception {
        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.post("/persons");
        String json = new ObjectMapper().writeValueAsString(new Person("Kalle", 18));
        request.content(json.getBytes());
        request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);
        // Assert the response status code and content
        assertEquals(400, response.getStatus());
    }


}
