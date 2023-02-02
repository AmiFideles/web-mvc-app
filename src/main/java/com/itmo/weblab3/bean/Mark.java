package com.itmo.weblab3.bean;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

@ManagedBean(name = "mark")
@ApplicationScoped
@Getter
@Setter
public class Mark {
    private int stepNumber;
    private int min;
    private int max;
    private double step;
    private double percent;
    private int position=6;
    private double XValue;
    private String YValue;
    private String RValues;
    private String graphX;
    private String graphY;
    private Map<Integer, Boolean> rValue = initR(1, 5);


    public Mark(){
        this.stepNumber=12;
        this.min=-3;
        this.max=3;
        this.step=0.5;
        this.percent=100d/stepNumber;
        this.position=6;

    }

    private static Map<Integer, Boolean> initR(int min, int max) {
        Map<Integer, Boolean> rValues = new LinkedHashMap<>();
        IntStream.rangeClosed(min, max).forEach(r -> {
            rValues.put(r, false);
        });
        return rValues;
    }

    public void setPosition(int position) {
        this.position = position;
        this.XValue = getXValue();
    }

    public double getXValue() {
        return getPosition()*step+min;
    }

    public void setXValue(double XValue) {
        this.XValue = XValue;
    }

    public void setYValue(String YValue) {
        this.YValue = YValue;
    }

    public void setRValues(String RValues) {
        this.RValues = RValues;
    }
}
