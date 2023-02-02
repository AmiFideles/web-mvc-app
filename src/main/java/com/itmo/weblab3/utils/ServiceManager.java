package com.itmo.weblab3.utils;


import com.itmo.weblab3.repository.HitRepository;
import com.itmo.weblab3.service.HitService;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

/**
 * @author Iskandarov Shakhzodbek P3133
 */

public class ServiceManager {
    private HitService hitService;


    public ServiceManager() {
        initAllServices();
    }

    //TODO подумать как исправить этот ужас. Синглтон? Плохо. А что тогда делать

    private void initAllServices() {
        try {
            SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            TransactionInterceptor transactionInterceptor = new TransactionInterceptor(sessionFactory);
            HitRepository userRepository = new HitRepository(session);
            hitService = new ByteBuddy()
                    .subclass(HitService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(transactionInterceptor))
                    .make()
                    .load(HitService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HitService getHitService() {
        return hitService;
    }
}
