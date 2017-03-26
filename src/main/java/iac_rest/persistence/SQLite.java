/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iac_rest.persistence;

import iac_rest.model.NormVerd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Matthias
 */
public class SQLite {

    // Connections and statements will be surounded by Try & Catch in order to close the connection (Java 7 feature)
    
    private Connection connect() {
        String url = "jdbc:sqlite:iac.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");  
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public void connectionInit() {

        try (Connection connection = this.connect();
            Statement stmt = connection.createStatement()) {
            
            stmt.setQueryTimeout(30);  // timeout is set to 30 seconds.

            String sql = "CREATE TABLE IF NOT EXISTS normaal_verdeling(\n" +
                        "   ID 			INTEGER PRIMARY KEY     	NOT NULL,\n" +
                        "   resultaat      	BOOLEAN NOT NULL CHECK (resultaat IN (0,1)),\n" +
                        "   smirnov_p_value     REAL     			NOT NULL,\n" +
                        "   kurtosis        	REAL,\n" +
                        "   skewness            REAL\n" +
                        ");";
            
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
    }
    
    public NormVerd get(int id){
        String sql = "SELECT * "
                     + "FROM normaal_verdeling WHERE ID = ?";
        
        NormVerd normverd = null;
        
        try (Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, id);

            ResultSet rs  = pstmt.executeQuery();
            
            normverd = new NormVerd();
            normverd.setId(rs.getInt("ID"));
            normverd.setResult(rs.getBoolean("resultaat"));
            normverd.setSmirnov_p_value(rs.getDouble("smirnov_p_value"));
            normverd.setKurtosis(rs.getDouble("kurtosis"));
            normverd.setSkewness(rs.getDouble("skewness"));
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return normverd;
    }
    
    public List<NormVerd> getAll() {
        String sql = "SELECT * "
                     + "FROM normaal_verdeling";
        
        List<NormVerd> normverdList = new ArrayList<>();
        
        try (Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){

            ResultSet rs  = pstmt.executeQuery();
            
            while (rs.next()) {
                NormVerd normverd = new NormVerd();
                normverd.setId(rs.getInt("ID"));
                normverd.setResult(rs.getBoolean("resultaat"));
                normverd.setSmirnov_p_value(rs.getDouble("smirnov_p_value"));
                normverd.setKurtosis(rs.getDouble("kurtosis"));
                normverd.setSkewness(rs.getDouble("skewness"));
                normverdList.add(normverd);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return normverdList;
    }
    
    public int getCount() {
        String sql = "SELECT count(*) FROM normaal_verdeling";
        
        int result = 0;
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            result = pstmt.executeQuery().getInt(1);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    public int insert(NormVerd normverd) {
        String sql = "INSERT INTO normaal_verdeling (resultaat, smirnov_p_value, kurtosis, skewness) "
                + "VALUES(?, ?, ?, ?)";
        
        int id = 0;
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, normverd.getResult());
            pstmt.setDouble(2, normverd.getSmirnov_p_value());
            pstmt.setDouble(3, normverd.getKurtosis());
            pstmt.setDouble(4, normverd.getSkewness());
            pstmt.executeUpdate();
            
            id = conn.prepareStatement("select last_insert_rowid()").executeQuery().getInt(1);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
      
    public void update(NormVerd normverd) {
        String sql = "UPDATE normaal_verdeling SET resultaat = ?, "
                + "smirnov_p_value = ?, "
                + "kurtosis = ?, "
                + "skewness = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, normverd.getResult());
            pstmt.setDouble(2, normverd.getSmirnov_p_value());
            pstmt.setDouble(3, normverd.getKurtosis());
            pstmt.setDouble(4, normverd.getSkewness());
            pstmt.setInt(5, normverd.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM normaal_verdeling WHERE id = ?";
        
        int result = 0;
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result == 1;
    }
    
    public static void main(String[] args) {
        SQLite sql = new SQLite();
        sql.connectionInit();
        sql.get(1);
    }
}
