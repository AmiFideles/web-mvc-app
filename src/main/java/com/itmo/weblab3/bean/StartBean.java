package com.itmo.weblab3.bean;

import com.itmo.weblab3.DiApplication;
import spring.injector.CustomInjector;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class StartBean {
    public StartBean() {
        DiApplication diApplication = new DiApplication();
        diApplication.run(DiApplication.class);
        CustomInjector.getInstance();
    }
}
