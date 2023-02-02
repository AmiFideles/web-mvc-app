package com.itmo.weblab3.bean;

import com.itmo.weblab3.model.Hit;
import com.itmo.weblab3.service.HitResultFactory;
import com.itmo.weblab3.service.HitService;
import com.itmo.weblab3.service.exceptions.HitCreatingException;
import lombok.Getter;
import lombok.Setter;
import spring.injector.CustomInjector;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@ApplicationScoped
@ManagedBean
@Getter
@Setter
public class SubmitHandler {
    @ManagedProperty(value = "#{error}")
    private ErrorView errorView;
    @ManagedProperty(value = "#{mark}")
    private Mark mark;
    @ManagedProperty(value = "#{storage}")
    private Storage storage;
    private HitResultFactory hitResultFactory;
    public SubmitHandler() {
        HitService service = CustomInjector.getInstance().getService(HitService.class);
        this.hitResultFactory = CustomInjector.getInstance().getService(HitResultFactory.class);
    }



    public void submitHitResult() {
        System.out.println();
        mark.getRValue().forEach((key,value) ->{
            if(value){
                try {
                    Hit hit = hitResultFactory.createHit(String.valueOf(mark.getXValue()),
                            String.valueOf(mark.getYValue()),
                            String.valueOf(key));
                    storage.addHit(hit);
                } catch (HitCreatingException e) {
                    errorView.showError(e.getMessage());
                }
            }
        });
    }

    public void handGraphClick(){
        try {
            Hit hit = hitResultFactory.createHit(
                    String.valueOf(mark.getGraphX()),
                    String.valueOf(mark.getGraphY()),
                    String.valueOf(mark.getRValues()));
            storage.addHit(hit);
        } catch (HitCreatingException e) {
            errorView.showError(e.getMessage());
        }
    }

}
