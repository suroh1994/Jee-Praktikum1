/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.transport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class Response implements ResponseIF {
    private List<Serializable> result;
    private Exception error;

    public Response() {
        result = new ArrayList<>();
    }

    public Response(List<Serializable> result, Exception error) {
        this.result = result;
        this.error = error;
    }

    @Override
    public List<Serializable> getResult() {
        return result;
    }

    public void setResult(List<Serializable> result) {
        this.result = result;
    }
    
    @Override
    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    @Override
    public String toString() {
        String s = new String();
        if (result != null) {
            s = s.concat(result.toString());
        }
        
        if (error != null) {
            s = s.concat(error.getMessage());
        }
        
        return s;
    }
    
    
    
}
