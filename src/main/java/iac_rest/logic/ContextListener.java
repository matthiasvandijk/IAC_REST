/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iac_rest.logic;

import iac_rest.model.NormVerd;
import iac_rest.persistence.SQLite;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 *
 * @author Matthias
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SQLite init = new SQLite();
        init.connectionInit();
        
        if (init.getCount() < 5) {
            init.insert(new NormVerd(true, 1.05, 1.11, 0.07));
            init.insert(new NormVerd(false, 0.02, 1.11, 0.07));
            init.insert(new NormVerd(true, 0.42, 1.42, 0.12));
            init.insert(new NormVerd(true, 0.45, 0.15, 1.25));
            init.insert(new NormVerd(false, 0.72, 2.15, 1.25));
        }
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
    
}
