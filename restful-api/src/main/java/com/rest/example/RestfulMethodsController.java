package com.rest.example;

import com.rest.example.Error.CustomError;
import com.rest.example.Service.RestService;
import com.rest.example.model.Output;
import com.rest.example.model.Result;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;

@Controller("rest")
public class RestfulMethodsController {

    @Inject
    RestService service;

    //http://localhost:8080/rest/get
    @Operation(summary = "Returns a sample json")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON),
            responseCode = "400", description = "Invalid symbol in req"
    )
    @Tag(name= "get")
    @Get(uri="/get", produces = MediaType.APPLICATION_JSON)
    public Output getMethod() {

    return service.returnGetRestResp();
    }

    //http://localhost:8080/rest/get/<anyValue>
    @Operation(summary = "Returns a sample json with an input parameter")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON),
            responseCode = "400", description = "Invalid symbol in req"
    )
    @Tag(name= "get")
    @Get("{value}")
    public Output getMethodWithParameter(@PathVariable String value) {
        if(value.equalsIgnoreCase("NotSupported")) {
             CustomError customError = CustomError.builder().error(HttpStatus.NOT_FOUND.name())
                    .message("Unavailable quotes for provided symbol")
                    .status(HttpStatus.NOT_FOUND.getCode())
                    .path("/rest/"+value).build();

             return Output.builder().ce(customError).build();

        }else {
            return service.returnGetRestRespWithParameter(value);
        }
    }

    //http://localhost:8080/rest/put, context-type:application/json, Body: Json
    @Operation(summary = "req and resp in json")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON),
            responseCode = "400", description = "Invalid symbol in req"
    )
    @Tag(name= "put")
    @Put(uri = "/put", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Output putMethodWithBody(@Body Result result) {

        return service.returnPutResponse(result);
    }

    //http://localhost:8080/rest/post, context-type:application/json, Body: Json
    @Operation(summary = "req and resp in json")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON),
            responseCode = "400", description = "Invalid symbol in req"
    )
    @Tag(name= "post")
    @Post(uri = "/post", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Output postMethodWithBody(@Body Result result) {

        return service.returnPutResponse(result);
    }

    @Operation(summary = "deletes json from a map based on key")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON),
            responseCode = "400", description = "Invalid symbol in req"
    )
    @Tag(name= "delete")
    @Delete(
            value = "/{name}",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    //http://localhost:8080/rest/{keyforDeletion}
    public String deleteMethod(@PathVariable String name) {

        return service.returnDeleteResponse(name);
    }
}
