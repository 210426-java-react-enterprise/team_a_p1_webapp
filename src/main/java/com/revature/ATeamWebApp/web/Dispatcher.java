package com.revature.ATeamWebApp.web;

import com.revature.ATeamWebApp.controller.UserController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dispatcher {
    private UserController userController = new UserController();
    
    public void dataDispatch(HttpServletRequest req, HttpServletResponse resp) {
        switch (req.getRequestURI()){
        
        }
    }
    
}
