/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implements the Services provided by this server.
 * 
 * @author Sebastian, Lars
 */
public class DBService implements IService {

    private List<Serializable> generateAndExecuteSQL(List<String> columns, List<Integer> args) throws Exception {
        final List<Serializable> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pizzadienst?"
                    + "user=root&password=");
            
            String sql = "SELECT ";
            for (String col : columns) {
                sql += col + ",";
            }
            sql = sql.substring(0, sql.length() - 1) + " FROM ordertable WHERE Phonenumber = ? AND Ordernumber = ?";
            
            final PreparedStatement pStmt = conn.prepareStatement(sql);
            int i;
            for (i = 0; i < args.size(); i++) {
                pStmt.setInt(i + 1, args.get(i));
            }

            final ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                for (i = 0; i < columns.size(); i++) {
                    System.out.println(columns.get(i) + " : " + rs.getString(columns.get(i)));
                    result.add(rs.getString(columns.get(i)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("An error occured while retrieving data from the database.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    @Override
    public List<Serializable> getRow(Integer phonenumber, Integer ordernumber) throws Exception {
        return generateAndExecuteSQL(Arrays.asList(new String[] {"Phonenumber","Ordernumber","Mealtype","Mealnumber","Date","Time"}),
                Arrays.asList(new Integer[]{phonenumber, ordernumber}));
    }

    @Override
    public List<Serializable> getDate(Integer phonenumber, Integer ordernumber) throws Exception {
        return generateAndExecuteSQL(Arrays.asList(new String[]{"Date"}),
                Arrays.asList(new Integer[]{phonenumber, ordernumber}));
    }

    @Override
    public List<Serializable> getTime(Integer phonenumber, Integer ordernumber) throws Exception {
        return generateAndExecuteSQL(Arrays.asList(new String[]{"Time"}),
                Arrays.asList(new Integer[]{phonenumber, ordernumber}));
    }

    @Override
    public List<Serializable> getMealtype(Integer phonenumber, Integer ordernumber) throws Exception {
        return generateAndExecuteSQL(Arrays.asList(new String[]{"Mealtype"}),
                Arrays.asList(new Integer[]{phonenumber, ordernumber}));
    }

    @Override
    public List<Serializable> getMealnumber(Integer phonenumber, Integer ordernumber) throws Exception {
        return generateAndExecuteSQL(Arrays.asList(new String[]{"Mealnumber"}),
                Arrays.asList(new Integer[]{phonenumber, ordernumber}));
    }

    @Override
    public List<Serializable> getDateAndTime(Integer phonenumber, Integer ordernumber) throws Exception {
        return generateAndExecuteSQL(Arrays.asList(new String[]{"Date", "Time"}),
                Arrays.asList(new Integer[]{phonenumber, ordernumber}));
    }

    @Override
    public List<Serializable> getMealtypeAndMealnumber(Integer phonenumber, Integer ordernumber) throws Exception {
        return generateAndExecuteSQL(Arrays.asList(new String[]{"Mealtype", "Mealnumber"}),
                Arrays.asList(new Integer[]{phonenumber, ordernumber}));
    }

}
