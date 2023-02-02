package com.itmo.weblab3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
@NoArgsConstructor
public class HitDTO {
    private long id;
    private double xValue;
    private double yValue;
    private double rValue;
    private boolean result;
}
