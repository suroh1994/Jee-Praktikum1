/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.reflection;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jeepraktikum1.services.DBService;

/**
 * Handels service requests
 *
 * @author Lars
 */
public class ServiceRequestHandler {
    
	/**
	 * processes the given list and invokes the requested method if the lists structure is valid.
	 * @param request the request as list
	 * @return the result of the request or an excpetion object if the request caused an exception.
	 */
	public static Serializable invokeRequestedServiceIfValid(final List<?> request) {
		Method method = null;
		Serializable result = null;
        try {
            final String methodName = (String) request.get(0);
            System.out.println("Requested method: " + methodName);
            
            final List<Serializable> params = new ArrayList<>();
            final List<Class<?>> paramClasses = new ArrayList<>();

            for (int i = 1; i < request.size(); i++) {
            	if(request.get(i) instanceof Serializable) {
            		final Serializable arg = (Serializable) request.get(i);
                    System.out.println("passed arg[" + (i-1) + "]: " + arg + " (" + arg.getClass() + ")");
            		params.add(arg);
            		paramClasses.add(arg.getClass());
            	} else {
            		throw new ClassCastException("One of the passed parameters does not implement " + Serializable.class.getSimpleName());
            	}
            }

            final Class<?> c = Class.forName(DBService.class.getName());
            final Object serviceInstance = c.newInstance();

            method = c.getDeclaredMethod(methodName, paramClasses.toArray(new Class<?>[paramClasses.size()]));
            
            result = (Serializable) method.invoke(serviceInstance, params.toArray());
            
        // Methods invocation threw exception (i.e. database down)
        } catch (InvocationTargetException ite) {
        	System.err.println("Invokation of method '" + method.getName() + "' threw exception: ");
        	ite.printStackTrace();
            result = new Exception( "The processing of the requested method '" + method.getName() + "' threw an exception.", ite.getCause());
        
        // the request caused an reflective exception (i.e. wrong parameters passed)
        } catch (ReflectiveOperationException roe) {
            System.err.println("Error occured while doing reflection on passed request!");
            roe.printStackTrace();
            result = new Exception("Failed to invoke requested method on server. Check your requested method signature and your parameters again!");
            
        // an unhandled exception occured ...
        } catch (Exception e) {
        	System.err.println("An unhandled excpetion was thrown during reflective method invokation.");
        	e.printStackTrace();
        	result = new Exception("An unknown excpetion occured on server. Please contact an admin or try again later ...");
        }
        return result;
    }
    
}
