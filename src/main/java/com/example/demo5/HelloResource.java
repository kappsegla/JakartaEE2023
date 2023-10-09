package com.example.demo5;

import com.example.demo5.interceptor.Logging;
import com.example.demo5.service.GreetingService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
//@Interceptors(LoggingInterceptor.class) //Replaces defining our own annotation
@Logging
public class HelloResource {

    private GreetingService greetingService;

    private static final Logger logger = LoggerFactory.getLogger(HelloResource.class);

    public HelloResource(){}

    @Inject
    public HelloResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GET
    @Produces("text/plain")
    @Path("/hello")
    public String hello() {
        return greetingService.greeting();
    }

    @GET
    @Path("/persons")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> nameAPerson() {
        return List.of(new Person("Kalle", 40));
    }

    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_PLAIN)
    public String query(@QueryParam("input") String input) {
        return input;
    }

    @GET
    @Path("/persons/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPersonByName(@PathParam("name") String name) {
        if (name == null || name.isEmpty())
            logger.error("/people name must not be empty or null");

        logger.info("/persons/" + name + " called");


        return new Person(name, 10);
    }

    @GET
    @Path("/persons/kalle")
    public Person getPersonByNameAndAge() {
        return new Person("Test", 10);
    }

    @GET
    @Path("/customresponse")
    @Produces(MediaType.TEXT_PLAIN)
    public Response customResponseCode() {
        return Response.status(200).entity("Test").header("MyHeader", "Testing").build();
    }

    @POST
    @Path("/persons")
    @Consumes(MediaType.APPLICATION_JSON)
    public void uploadPerson(Person person){
        throw new MyException("Invalid id");
//        logger.info("Person uploaded:" + person);
    }
}
