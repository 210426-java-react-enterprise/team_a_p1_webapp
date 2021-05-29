package com.revature.ATeamWebApp.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import com.revature.ATeamORM.datasource.ConnectionFactory;
import com.revature.ATeamORM.datasource.Session;
import com.revature.ATeamORM.repos.ObjectRepo;
import com.revature.ATeamWebApp.dtos.Credentials;
import com.revature.ATeamWebApp.exceptions.AuthenticationException;
import com.revature.ATeamWebApp.models.AppUser;
import com.revature.ATeamWebApp.services.UserService;
import com.revature.ATeamWebApp.util.datasource.ConnectionSQL;
import com.revature.ATeamWebApp.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;


public class AuthServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger();

    private final UserService userService;

    public AuthServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Credentials creds = mapper.readValue(req.getInputStream(), Credentials.class);
            logger.info("Attempting to authenticate user, %s, with provided credentials", creds.getUsername());
            
            ConnectionSQL c = new ConnectionSQL();
            Connection conn = ConnectionFactory.getInstance()
                                               .getConnection(c);
            ObjectRepo or = new ObjectRepo();
            //userService.authenticate(creds.getUsername(), creds.getPassword());
           // AppUser authUser = new AppUser(creds.getUsername(),creds.getPassword());
    
            
            Session session = new Session(ConnectionSQL.class);
            AppUser authUser = session.find(AppUser.class,"username", creds.getUsername()).getFirstEntry();
            
            //or.read(authUser);
            writer.write(mapper.writeValueAsString(authUser));
    
            

            req.getSession().setAttribute("this-user", authUser);

        } catch (MismatchedInputException e) {
            logger.warn(e.getMessage());
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            logger.warn(e.getMessage());
            resp.setStatus(401);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resp.setStatus(500);
        }
    }
}