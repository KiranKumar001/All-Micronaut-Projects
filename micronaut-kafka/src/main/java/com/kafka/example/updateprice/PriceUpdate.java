package com.kafka.example.updateprice;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Introspected
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceUpdate {

    private String model;
    private BigDecimal finalPrice;
}
