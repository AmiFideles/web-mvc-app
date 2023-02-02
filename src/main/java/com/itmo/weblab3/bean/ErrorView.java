package com.itmo.weblab3.bean;

import lombok.Setter;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * @author Iskandarov Shakhzodbek P3133
 */

@Setter
@ManagedBean(name = "error")
@ApplicationScoped
public class ErrorView {
    private String message;

    public String getMessage() {
        if (message==null || message.isEmpty()){
            return "Internal error occurred";
        }
        return message;
    }

    public void showError(String message){
        setMessage(message);
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath()+"/error.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
