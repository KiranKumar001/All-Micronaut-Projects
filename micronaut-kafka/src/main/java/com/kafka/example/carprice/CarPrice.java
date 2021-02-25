package com.kafka.example.carprice;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Introspected
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPrice {

    private String model;
    private BigDecimal totalPrice;
    private BigDecimal tax;

}
