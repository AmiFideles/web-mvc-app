package com.itmo.weblab3;


import spring.annotations.Application;
import spring.injector.CustomInjector;
@Application
public class DiApplication {
    private final CustomInjector injector;
    public DiApplication() {
        super();
        this.injector = CustomInjector.getInstance();
    }
    public void run(Class<?> mainClassz) {
        boolean hasCustomApplicationAnnotation =
                mainClassz.isAnnotationPresent(Application.class);
        if(hasCustomApplicationAnnotation) {
            System.out.println("Starting CustomDemoApplication...");
            this.startApplication(mainClassz);
        }else{
            System.out.println("\nRunning as regular java Application...");
        }
    }
    /**
     * Start application
     * @param mainClass
     */
    public void startApplication(Class<?> mainClass) {
        try {
            synchronized (DiApplication.class) {
                this.injector.initFramework(mainClass);
                System.out.println("\nCustomDemoApplication Started....");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void run(Class<?> mainClassz, String[] args){
        new DiApplication().run(mainClassz);
    }

    public CustomInjector getInjector() {
        return injector;
    }
}