/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.transport;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface ResponseIF extends Serializable {
    
    List<Serializable> getResult();
    Exception getError();
}
