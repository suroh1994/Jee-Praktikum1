/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.client.services;

import java.io.Serializable;
import jeepraktikum1.transport.ResponseIF;

/**
 *
 * @author Sebastian
 */
public interface IService extends Serializable {
    ResponseIF getRow(Integer phonenumber, Integer ordernumber);
    ResponseIF getDate(Integer phonenumber, Integer ordernumber);
    ResponseIF getTime(Integer phonenumber, Integer ordernumber);
    ResponseIF getMealtype(Integer phonenumber, Integer ordernumber);
    ResponseIF getMealnumber(Integer phonenumber, Integer ordernumber);
    ResponseIF getDateAndTime(Integer phonenumber, Integer ordernumber);
    ResponseIF getMealtypeAndMealnumber(Integer phonenumber, Integer ordernumber);
}
