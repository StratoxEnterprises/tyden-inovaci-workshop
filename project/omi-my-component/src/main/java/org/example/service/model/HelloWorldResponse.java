package org.example.service.model;

public class HelloWorldResponse {
    private final String greeting;

    public HelloWorldResponse(final String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }

}
