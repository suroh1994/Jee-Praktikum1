/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.server.services;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface IService extends Serializable {
    List<Serializable> getRow(Integer phonenumber, Integer ordernumber) throws Exception;
    List<Serializable> getDate(Integer phonenumber, Integer ordernumber) throws Exception;
    List<Serializable> getTime(Integer phonenumber, Integer ordernumber) throws Exception;
    List<Serializable> getMealtype(Integer phonenumber, Integer ordernumber) throws Exception;
    List<Serializable> getMealnumber(Integer phonenumber, Integer ordernumber) throws Exception;
    List<Serializable> getDateAndTime(Integer phonenumber, Integer ordernumber) throws Exception;
    List<Serializable> getMealtypeAndMealnumber(Integer phonenumber, Integer ordernumber) throws Exception;
}
