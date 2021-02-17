package com.kafka.example.carprice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPrice {

    private String model;
    private BigDecimal totalPrice;
    private BigDecimal tax;

}
