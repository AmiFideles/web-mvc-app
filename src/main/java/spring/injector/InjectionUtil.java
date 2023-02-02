package spring.injector;

import spring.annotations.Autowired;
import spring.annotations.Qualifier;

import javax.management.RuntimeErrorException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InjectionUtil {

    public static void autowire(Class<?> classz,
                                Object classInstance, Map<Class<?>, Object> applicationScope,
                                Map<Class<?>, Class<?>> diMap)
            throws InstantiationException, IllegalAccessException {
        Set<Field> fields = findFields(classz);
        for (Field field : fields) {
            String qualifier =
                    field.isAnnotationPresent(Qualifier.class)
                            ? field.getAnnotation(Qualifier.class).value() : null;
            Object fieldInstance = getBeanInstance(field.getType(), diMap,
                    applicationScope, field.getName(), qualifier);
            field.set(classInstance, fieldInstance);

            autowire(fieldInstance.getClass(),
                    fieldInstance, applicationScope, diMap);
        }
    }


    public static <T> Object getBeanInstance(Class<T> interfaceClass,
                                             Map<Class<?>, Class<?>> diMap,
                                             Map<Class<?>, Object> applicationScope, String fieldName,
                                             String qualifier)
            throws InstantiationException, IllegalAccessException {

        Class<?> implementationClass =
                getImplimentationClass(interfaceClass, diMap,
                        fieldName, qualifier);
        if (applicationScope.containsKey(implementationClass)) {
            return applicationScope.get(implementationClass);
        }

        Object service = implementationClass.newInstance();
        applicationScope.put(implementationClass, service);
        return service;
    }


    private static Class<?> getImplimentationClass(Class<?>
                                                           interfaceClass, Map<Class<?>, Class<?>> diMap,
                                                   String fieldName, String qualifier) {
        Set<Map.Entry<Class<?>, Class<?>>> implementationClasses =
                diMap.entrySet().stream()
                        .filter(entry -> entry.getValue() == interfaceClass)
                        .collect(Collectors.toSet());

        String errorMessage = "";
        if (implementationClasses == null ||
                implementationClasses.isEmpty()) {
            errorMessage = "no implementation found for interface "
                    + interfaceClass.getName();
        } else if (implementationClasses.size() == 1) {
            Optional<Map.Entry<Class<?>, Class<?>>> optional =
                    implementationClasses.stream().findFirst();

            if (optional.isPresent()) {
                return optional.get().getKey();
            }
        } else if (implementationClasses.size() > 1) {
            final String findBy = (qualifier == null ||
                    qualifier.trim().length() == 0) ? fieldName : qualifier;
            Optional<Map.Entry<Class<?>, Class<?>>> optional =
                    implementationClasses.stream().filter(entry ->
                            entry.getKey().getSimpleName()
                                    .equalsIgnoreCase(findBy)).findAny();

            if (optional.isPresent()) {
                return optional.get().getKey();
            } else {
                errorMessage = "There are " + implementationClasses.size()
                        + " of interface " + interfaceClass.getName()
                        + " Expected single implementation or make use of "
                        + "@CustomQualifier to resolve conflict";
            }
        }
        throw new RuntimeErrorException(new Error(errorMessage));
    }


    private static Set<Field> findFields(Class<?> classz) {
        Set<Field> set = new HashSet<>();
        while (classz != null) {
            for (Field field : classz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    set.add(field);
                }
            }
            classz = classz.getSuperclass();
        }
        return set;
    }
}