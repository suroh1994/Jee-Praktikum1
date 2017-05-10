/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.client.transport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class ProtocolParser {
    
    public static List<Serializable> parseResponse(Object obj) throws Exception {
        if (obj instanceof ArrayList<?>) {
            return (ArrayList<Serializable>) obj;
        } else if (obj instanceof Exception) {
            throw (Exception) obj;
        } else {
            throw new IllegalArgumentException("Received object cannot be parsed.");
        }
    }
    
    public static List<Serializable> makeRequest(String method, List<Serializable> args) {
        args.add(0, method);
        return new ArrayList<>(args);
    }
}
