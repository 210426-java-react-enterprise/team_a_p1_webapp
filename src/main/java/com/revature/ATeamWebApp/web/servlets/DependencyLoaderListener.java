package com.revature.ATeamWebApp.web.servlets;

import com.revature.ATeamORM.datasource.Session;
import com.revature.ATeamWebApp.services.UserService;
import com.revature.ATeamWebApp.util.datasource.ConnectionSQL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class DependencyLoaderListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //some dependecy injection at the start of tomcat
        Session session = null;
        try {
            session = new Session(ConnectionSQL.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        UserService userService = new UserService(session);
        
        AuthServlet authServlet = new AuthServlet(userService);
        UserServlet userServlet = new UserServlet(userService);
        
        //when the "event" of when context has loaded, we get access  to it
        //which then we can add servlets and map them.
        ServletContext context = servletContextEvent.getServletContext();
        context.addServlet("AuthServlet",authServlet).addMapping("/auth");
        context.addServlet(("UserServlet"),userServlet).addMapping("/users");
    
    
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    
    }
}
