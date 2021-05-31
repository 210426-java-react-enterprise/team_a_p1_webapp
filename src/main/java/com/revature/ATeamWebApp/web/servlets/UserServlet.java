package com.revature.ATeamWebApp.web.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.ATeamWebApp.models.AppUser;
import com.revature.ATeamWebApp.services.UserService;
import com.revature.ATeamWebApp.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;


public class UserServlet extends HttpServlet {
    
    private final Logger logger = Logger.getLogger();
    private final UserService  userService;
    
    public UserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        AppUser requestingUser = (session == null) ? null : (AppUser) session.getAttribute("this-user");

        if (requestingUser != null) {
            resp.setStatus(401);
            return;
        }
        try{
    
            AppUser regUser = mapper.readValue(req.getInputStream(),AppUser.class);
    
            if(!userService.isUserValid(regUser)){
                resp.setStatus(406);
                return;
            }else if(!userService.isUsernameEmailAvailable(regUser)){
                resp.setStatus(400);
                return;
            }
    
            userService.register(regUser);
            writer.write("Newly created Users.");
            writer.write(mapper.writeValueAsString(regUser));
        }catch (MismatchedInputException e){
            logger.warn(e.getMessage());
            resp.setStatus(400);
        }
        
        
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);//false: do not create session get one if exist, if not create one
        
        //attribute name must match the one we set for auth servlet, which is when we setAttribute.
        AppUser requestingUser = (session == null) ? null : (AppUser) session.getAttribute("this-user");
        
        //if null there is no session and we do know know who you are!
        if (requestingUser == null) {
            resp.setStatus(401);
            return;
        //we do know who you are but you do not have access!!
        } else if (!requestingUser.getUsername().equals("AlphaManager")) {
            resp.setStatus(403);
            return;
        }
        
        try{
            
            String userIdParam = req.getParameter("id");
            
            if(userIdParam == null){
                List<AppUser> users = userService.getAllUsers();
                writer.write(mapper.writeValueAsString(users));
            }else{
                AppUser singleUser = userService.getUser("id",userIdParam);
                writer.write(mapper.writeValueAsString(singleUser));
            }
        
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
    
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        AppUser requestingUser = (session == null) ? null : (AppUser) session.getAttribute("this-user");

        if (requestingUser == null) {
            resp.setStatus(401);
            return;
        } else if (!requestingUser.getUsername().equals("AlphaManager")) {
            resp.setStatus(403);
            return;
        }
        
        try{
        
        }

        /*String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        int age = Integer.parseInt(req.getParameter("age"));

        AppUser updatedUser = new AppUser();
        updatedUser.setUsername(username);
        updatedUser.setPassword(password);
        updatedUser.setEmail(email);
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setAge(age);

        userService.update(updatedUser);
*/

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        AppUser requestingUser = (session == null) ? null : (AppUser) session.getAttribute("this-user");

        if (requestingUser == null) {
            resp.setStatus(401);
            return;
        } else if (!requestingUser.getUsername().equals("wsingleton")) {
            resp.setStatus(403);
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        int age = Integer.parseInt(req.getParameter("age"));

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);

        userService.delete(user);

    }
}
