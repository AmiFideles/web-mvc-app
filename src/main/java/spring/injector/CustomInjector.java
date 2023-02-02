package spring.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


import com.itmo.weblab3.service.HitService;
import com.itmo.weblab3.utils.HibernateUtils;
import com.itmo.weblab3.utils.TransactionInterceptor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.reflections.Reflections;
import spring.annotations.Component;
import spring.annotations.Repository;
import spring.annotations.Service;


public class CustomInjector {
    private Map<Class<?>, Class<?>> diMap;
    private Map<Class<?>, Object> applicationScope;
    public static CustomInjector customInjector;

    private CustomInjector() {
        super();
        diMap = new ConcurrentHashMap<>();
        applicationScope = new ConcurrentHashMap<>();
    }

    public static CustomInjector getInstance() {
        if (customInjector == null) {
            customInjector = new CustomInjector();
        }
        return customInjector;
    }

    public <T> T getService(Class<T> classz) {
        try {
            return this.getBeanInstance(classz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void initFramework(Class<?> mainClass)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, IOException, NoSuchMethodException, InvocationTargetException {
        Class<?>[] classes =
                ClassLoaderUtil.getClasses(mainClass.getPackage().getName());
        Reflections reflections = new
                Reflections(mainClass.getPackage().getName());
        Set<Class<?>> types =
                reflections.getTypesAnnotatedWith(Component.class);
        types.addAll(reflections.getTypesAnnotatedWith(Service.class));
        types.addAll(reflections.getTypesAnnotatedWith(Repository.class));
        for (Class<?> implementationClass : types) {
            Class<?>[] interfaces = implementationClass.getInterfaces();
            if (interfaces.length == 0) {
                diMap.put(implementationClass, implementationClass);
            } else {
                for (Class<?> iface : interfaces) {
                    diMap.put(implementationClass, iface);
                }
            }
        }
        /*знаю что это ужасно. Но что поделать. Надо было за ночь исправить. Потом переделаю нормально.
      В целом  идейно я понял как нужно делать все, что от меня требовалось,  но реализовать все равно не просто*/
        for (Class<?> classz : classes) {
            if (classz.isAnnotationPresent(Repository.class)) {
                Constructor<?> declaredConstructor = classz.getDeclaredConstructor(Session.class);
                SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
                Object classInstance = declaredConstructor.newInstance((Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)));
                applicationScope.put(classz, classInstance);
                InjectionUtil.autowire(classz, classInstance,
                        applicationScope, diMap);
            }
        }

        for (Class<?> classz : classes) {
            if (classz.isAnnotationPresent(Service.class)) {
                TransactionInterceptor transactionInterceptor = new TransactionInterceptor(HibernateUtils.getSessionFactory());
                Object service = new ByteBuddy()
                        .subclass(classz)
                        .method(ElementMatchers.any())
                        .intercept(MethodDelegation.to(transactionInterceptor))
                        .make()
                        .load(HitService.class.getClassLoader())
                        .getLoaded()
                        .getDeclaredConstructor()
                        .newInstance();

                applicationScope.put(classz, service);
                InjectionUtil.autowire(classz, service,
                        applicationScope, diMap);
            }
        }

        for (Class<?> classz : classes) {
            if (classz.isAnnotationPresent(Component.class)) {
                Object classInstance = classz.newInstance();
                applicationScope.put(classz, classInstance);
                InjectionUtil.autowire(classz, classInstance,
                        applicationScope, diMap);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getBeanInstance(Class<T> interfaceClass) throws
            InstantiationException, IllegalAccessException {
        return (T) InjectionUtil.getBeanInstance(interfaceClass,
                diMap, applicationScope, null, null);
    }

    public Map<Class<?>, Object> getApplicationScope() {
        return applicationScope;
    }

    public Map<Class<?>, Class<?>> getDiMap() {
        return diMap;
    }

    public void setBean(Class<?> clazz, Object object) {
        applicationScope.put(clazz, object);
    }
}