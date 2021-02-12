package com.rest.example.Service;

import com.rest.example.model.Output;
import com.rest.example.model.Result;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class RestService {

    public Map<String, Result> mapValue = new HashMap<>();

    public Output returnGetRestResp() {


        return Output.builder()
                .name("micronaut-rest-api-get")
                .value("Hello World!!!")
                .build();
    }

    public Output returnGetRestRespWithParameter(String value) {

        return Output.builder()
                .name(value)
                .value("Hello World!!!")
                .build();
    }

    public Output returnPutResponse(Result result) {

        mapValue.put(result.getVal(), result);

        return Output.builder()
                .name(result.getVal())
                .value("Total number of records created"+mapValue.size())
                .build();
    }

    public String returnDeleteResponse(String name) {

        mapValue.remove(name);

        return "Record Deleted!!! TotalRecords present now is: "+getListSize();

    }

    public int getListSize() {

        return mapValue.size();
    }
}
