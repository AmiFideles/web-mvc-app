package com.itmo.weblab3.service;


import com.itmo.weblab3.service.exceptions.WrongValueException;
import spring.annotations.Component;

import java.util.Arrays;

/**
 * Проверяет на null введенные данные
 */
@Component
public class Validator {
    private final double yMin;
    private final double yMax;
    private final Integer[] rValues;
    private final Double[] xValues;

    public Validator() {
        this.yMin=-5;
        this.yMax=3;
        this.rValues = new Integer[]{1,2,3,4,5};

        this.xValues= new Double[]{-3d,-2.5d,-2d, -1.5d, -1d, -0.5d, 0d,0.5d, 1d, 1.5d,2d, 2.5d, 3d};

    }

    public boolean validateValues(double x, double y, int r) throws WrongValueException {
        return validateXValue(x) && validateYValue(y) && validateRValue(r);
    }

    private boolean validateXValue(double xValue) throws WrongValueException {
        if (Arrays.asList(xValues).contains(xValue)) return true;
        throw new WrongValueException("Wrong x value");
    }

    private boolean validateRValue(int rValue) throws WrongValueException {
        if (Arrays.asList(rValues).contains(rValue)) return true;
        throw new WrongValueException("Wrong r value");
    }

    private boolean validateYValue(double yValue) throws WrongValueException {
        if (yValue>=yMin && yValue<=yMax) return true;
        throw new WrongValueException("Wrong y value");
    }
}


