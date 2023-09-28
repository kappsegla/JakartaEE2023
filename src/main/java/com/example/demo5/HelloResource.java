package com.example.demo5;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    private GreetingService greetingService;

    public HelloResource(){}

    @Inject
    public HelloResource(GreetingService greetingService){
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
    public List<Person> nameAPerson(){
        return List.of(new Person("Kalle", 40));
    }
    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_PLAIN)
    public String query( @QueryParam("input") String input){
        return input;
    }

    @GET
    @Path("/persons/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPersonByName(@PathParam("name") String name){
       return new Person(name, 10);
    }
    @GET
    @Path("/persons/kalle")
    public Person getPersonByNameAndAge(){
       return new Person("Test", 10);
    }
    @GET
    @Path("/customresponse")
    @Produces(MediaType.TEXT_PLAIN)
    public Response customResponseCode(){
        return Response.status(200).entity("Test").header("MyHeader","Testing").build();
    }

}
