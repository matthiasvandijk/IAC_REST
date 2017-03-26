/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iac_rest.logic;

import iac_rest.model.MyFault;
import iac_rest.model.NormVerd;
import java.util.List;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.TestUtils;

/**
 *
 * @author Matthias
 */
public class NormaalVerdCalculator {
    private List<Double> data;

    public NormaalVerdCalculator(List<Double> data) {
        this.data = data;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }
    
    public NormVerd calculate() throws MyFault {
        //Service Requirements 
        if (data.isEmpty()) {
            throw new MyFault("No data is provided");
        } else if (data.size() < 2) {
            throw new MyFault("A minimum of two data elements is required.");
        }
        
        //Declaring Apache Commons DescriptiveStatistics
        DescriptiveStatistics stats = new DescriptiveStatistics();

        //Filling DescriptiveStatistics class with the provided dataset
        for (int i = 0; i < data.size(); i++) {
            stats.addValue(data.get(i));
        }
        
        //Let the DescriptiveStatistics class calculate the mean and standard deviation
        double mean = stats.getMean();
        double std = stats.getStandardDeviation();        
        
        //Implementing the Kolmogorov–Smirnov test & calculating the kurtosis and skewness
        NormalDistribution x = new NormalDistribution(mean, std);
        double p_value = TestUtils.kolmogorovSmirnovTest(x, stats.getValues(), false);
        double kurtosis = stats.getKurtosis();
        double skewness = stats.getSkewness();
        boolean result = false;
        
        //Check if the dataset is a normal distribution:
        //Kolmogorov–Smirnov p_value should be >= 0.05
        //Both kurtosis and skewness should be between -2.0 and 2.0
        if (kurtosis < 2.0 && kurtosis > -2.0
                && skewness < 2.0 && skewness > -2.0
                && p_value >= 0.05) {
            result = true;
        }
        
        //Calculation done, results put inside a normal distribution object:
        NormVerd  nvm = new NormVerd(result, p_value, kurtosis, skewness);
        
        return nvm;
    }
}
