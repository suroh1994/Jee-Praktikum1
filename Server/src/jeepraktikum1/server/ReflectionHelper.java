/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.server;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import jeepraktikum1.server.services.DBService;
import jeepraktikum1.transport.RequestIF;
import jeepraktikum1.transport.Response;
import jeepraktikum1.transport.ResponseIF;

/**
 *
 * @author Sebastian
 */
public class ReflectionHelper {
    
    public static Object doReflection(List<Serializable> request) {
        try {
            
            for(Serializable s : request) {
            System.out.println("Class: " + s.getClass() + ", Obj: " + s);
        }            
            final String methodName = (String) request.get(0);
            System.out.println("Requested method: " + methodName);
            final List<Serializable> params = new ArrayList<>();
            for (int i = 1; i < request.size(); i++) {
                params.add(request.get(i));
            }
            final List<Class<?>> paramClasses = new ArrayList<>();

            System.out.println("Passed Arguments:");

            for(Serializable s : params) {
                System.out.println("Class: " + s.getClass() + ", obj: " + s);
                paramClasses.add(s.getClass());
            }

            final Class<?> c = Class.forName(DBService.class.getName());
            final Object serviceInstance = c.newInstance();

            final Method method = c.getDeclaredMethod(methodName, paramClasses.toArray(new Class<?>[paramClasses.size()]));
            return (List<Serializable>) method.invoke(serviceInstance, params.toArray());
        } catch (Exception e) {
            System.err.println("Error occured while doing reflection on passed request!");
            e.printStackTrace();
            return new Exception( "Exceptiontype on Server: " + e.getClass().getName() );
        }
    }
    
}
