package com.itmo.weblab3.service;

import com.itmo.weblab3.model.Hit;
import com.itmo.weblab3.service.exceptions.HitCreatingException;
import com.itmo.weblab3.service.exceptions.WrongValueException;
import lombok.NoArgsConstructor;
import spring.annotations.Autowired;
import spring.annotations.Component;

/**
 * будет создавать hit , использовать внутри себя другие сервисы
 */
@Component
@NoArgsConstructor
public class HitResultFactory {
    @Autowired
    private Checker checker;
    @Autowired
    private Validator validator;

    public Hit createHit(String xValue, String yValue, String rValue) throws HitCreatingException {
        double x = parseValue(xValue);
        double y = parseValue(yValue);
        int r = parseValueToInt(rValue);

        try {
            validator.validateValues(x, y, r);
            boolean hitResult = checker.checkHit(x, y, r);
            Hit hit = new Hit(0, x, y, r, hitResult);
 /*           Hit hit = Hit.builder().xValue(x).yValue(y).rValue(r).result(hitResult).build();*/
            return hit;
        } catch (WrongValueException e) {
            throw new HitCreatingException(e.getMessage());
        }
    }

    public double parseValue(String inputValue) throws HitCreatingException {
        try {
            if (inputValue==null){
                throw new HitCreatingException("Cannot parse input value to double");
            }
            return Double.parseDouble(inputValue);
        } catch (NumberFormatException e) {
            throw new HitCreatingException("Cannot parse input value to double");
        }
    }

    public int parseValueToInt(String inputValue) throws HitCreatingException {
        try {
            if (inputValue==null){
                throw new HitCreatingException("Cannot parse input value to double");
            }
            return Integer.parseInt(inputValue);
        } catch (NumberFormatException e) {
            throw new HitCreatingException("Cannot parse input value to double");
        }
    }

}
