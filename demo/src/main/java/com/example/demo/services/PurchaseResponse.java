package com.example.demo.services;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PurchaseResponse {
    private final String orderTrackingNumber;
}
