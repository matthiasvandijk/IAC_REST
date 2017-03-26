/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iac_rest.model;

import java.util.List;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Matthias
 */
public class NormVerd {
    private int id;
    private boolean result;
    private double smirnov_p_value;
    private double kurtosis;
    private double skewness;

    public NormVerd(int id, boolean result, double smirnov_p_value, double kurtosis, double skewness) {
        this.id = id;
        this.result = result;
        this.smirnov_p_value = smirnov_p_value;
        this.kurtosis = kurtosis;
        this.skewness = skewness;
    }
    
    // Constructor for NormVerd without ID
    public NormVerd(boolean result, double smirnov_p_value, double kurtosis, double skewness) {
        this.result = result;
        this.smirnov_p_value = smirnov_p_value;
        this.kurtosis = kurtosis;
        this.skewness = skewness;
    }
  
    public NormVerd() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getSmirnov_p_value() {
        return smirnov_p_value;
    }

    public void setSmirnov_p_value(double smirnov_p_value) {
        this.smirnov_p_value = smirnov_p_value;
    }

    public double getKurtosis() {
        return kurtosis;
    }

    public void setKurtosis(double kurtosis) {
        this.kurtosis = kurtosis;
    }

    public double getSkewness() {
        return skewness;
    }

    public void setSkewness(double skewness) {
        this.skewness = skewness;
    }

    @Override
    public String toString() {
        return "NormVerd{" + "id=" + id + ", result=" + result + ", smirnov_p_value=" + smirnov_p_value + ", kurtosis=" + kurtosis + ", skewness=" + skewness + '}';
    }
    
}
