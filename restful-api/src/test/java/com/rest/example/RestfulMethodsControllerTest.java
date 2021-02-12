package com.rest.example;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rest.example.Error.CustomError;
import com.rest.example.Service.RestService;
import com.rest.example.model.Output;
import com.rest.example.model.Result;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

public class RestfulMethodsControllerTest extends RestfulApiTest{

    @Inject
    @Client("/")
    RxHttpClient httpClient;

    @Inject
    RestService restService;

    @Test
    void testGetMethod() {

        final Output response = httpClient.toBlocking().retrieve("/rest/get", Output.class);
        Assertions.assertEquals("Hello World!!!", response.getValue());
        Assertions.assertEquals("micronaut-rest-api-get", response.getName());

    }

    @Test
    void testGetMethodWithParameter() {

        final ObjectNode response = httpClient.toBlocking().retrieve("/rest/myApp", ObjectNode.class);
        Assertions.assertTrue(!response.toString().isBlank());

    }

    @Test
    void testPutMethodWithBody() {

        Result result = Result.builder().val("This is a put method call").build();
        Assertions.assertEquals(0, restService.getListSize());
        HttpResponse<Object> response = httpClient.toBlocking().exchange(HttpRequest.PUT("/rest/put", result));

        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
        Assertions.assertEquals(1, restService.getListSize());

        restService.mapValue.remove(result.getVal());

    }

    @Test
    void testPostMethodWithBody() {

        Result result = Result.builder().val("This is a post method call").build();
        Assertions.assertEquals(0, restService.getListSize());
        HttpResponse<Object> response = httpClient.toBlocking().exchange(HttpRequest.POST("/rest/post", result));

        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
        Assertions.assertEquals(1, restService.getListSize());

        restService.mapValue.remove(result.getVal());

    }

    @Test
    void testDeleteMethod() {

        Result result = Result.builder().val("DeleteTest").build();
        Assertions.assertEquals(0, restService.getListSize());
        restService.mapValue.put(result.getVal(), result);
        Assertions.assertEquals(1, restService.getListSize());


        HttpResponse<Object> delResponse = httpClient.toBlocking().exchange(HttpRequest.DELETE("/rest/"+result.getVal()));
        Assertions.assertEquals(HttpStatus.OK, delResponse.getStatus());
        Assertions.assertEquals(0, restService.getListSize());

    }

    //trying to do get for an endpoint which dhas parameter value as NotSupported
    @Test
    void customErrorTest() {
        try {
            httpClient.toBlocking().retrieve(HttpRequest.GET("/rest/NotSupported"),
                    Argument.of(Output.class),
                    Argument.of(CustomError.class));
        } catch (HttpClientResponseException e) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getResponse().getStatus());
            Optional<CustomError> ce = e.getResponse().getBody(CustomError.class);
            Assertions.assertTrue(ce.isPresent());
            Assertions.assertEquals(404, ce.get().getStatus());
            Assertions.assertEquals("NOT_FOUND", ce.get().getError());
            Assertions.assertEquals("/quotes/quotes", ce.get().getPath());
            Assertions.assertEquals("Unavailable quotes for provided symbol", ce.get().getMessage());
        }
    }
}
