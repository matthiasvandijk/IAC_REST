/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import iac_rest.logic.NormaalVerdCalculator;
import iac_rest.model.Data;
import iac_rest.model.MyFault;
import iac_rest.model.NormVerd;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matthias
 */
public class ApplicationTest {
    
    public ApplicationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void NietNormaalVerdCalculatorTest() throws MyFault {
        List<Double> dataList = new ArrayList<>();
        dataList.add(3.12);
        dataList.add(15.3);
        dataList.add(13.1);
        dataList.add(16.6);
        dataList.add(17.13);
        dataList.add(65.53);
        dataList.add(95.6);
        dataList.add(185.12);
        dataList.add(443.52);
        dataList.add(943.46);
                
        NormaalVerdCalculator calculator = new NormaalVerdCalculator(dataList);
        NormVerd result = calculator.calculate();
        assertFalse(result.getResult());
        
    }
    
    @Test
    public void WelNormaalVerdCalculatorTest() throws MyFault {
        List<Double> dataList = new ArrayList<>();
        dataList.add(3.12);
        dataList.add(15.3);
        dataList.add(13.1);
        dataList.add(16.6);
        dataList.add(17.13);
        dataList.add(14.53);
        dataList.add(12.6);
        dataList.add(11.12);
        dataList.add(8.52);
        dataList.add(6.46);
        dataList.add(2.44);
                
        NormaalVerdCalculator calculator = new NormaalVerdCalculator(dataList);
        NormVerd result = calculator.calculate();
        assertTrue(result.getResult());
        
    }

   
}
