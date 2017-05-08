/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.transport;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class Request implements RequestIF {
    
    private String methodName;
    private List<Serializable> params;

    public Request(String methodName, List<Serializable> params) {
        this.methodName = methodName;
        this.params = Collections.unmodifiableList(params);
    }
    
    @Override
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public List<Serializable> getParams() {
        return params;
    }

    public void setParams(List<Serializable> params) {
        this.params = params;
    }
    
    
    
}
