package com.rest.example.model;

import com.rest.example.Error.CustomError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Output {

    private String name;

    private String value;

    private CustomError ce;
}
