package com.revature.ATeamWebApp.services;

import com.revature.ATeamORM.datasource.ConnectionFactory;
import com.revature.ATeamORM.datasource.Result;
import com.revature.ATeamORM.repos.ObjectRepo;
import com.revature.ATeamORM.datasource.Session;

import com.revature.ATeamWebApp.exceptions.*;
import com.revature.ATeamWebApp.models.AppUser;

import com.revature.ATeamWebApp.util.datasource.ConnectionSQL;
import com.revature.ATeamWebApp.util.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class UserService {
    
    private final Logger logger = Logger.getLogger();
    private final Session session;
    
    
    public UserService(Session session){
        
        this.session = session;
    }
    
    public List<AppUser> getAllUsers() {
        
            return session.findAllUsers(AppUser.class).getList();
    }
    
    
    public AppUser getUser(String fieldName, String fieldValue) {
  
        //(session.open())
        try {
            return session.find(AppUser.class, fieldName, fieldValue).getFirstEntry();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new AuthenticationException();
        }
    }
    
    public AppUser authenticate(String username, String password) throws AuthenticationException, SQLException {
        
        try {
            
            AppUser user = session.find(AppUser.class, "username", username)
                                  .getFirstEntry();
            if (user != null && user.getPassword()
                                    .equals(password)) {
                return user;
            } else {
                throw new AuthenticationException();
            }
            
        } catch (SQLException | DataSourceException e) {
            logger.warn(e.getMessage());
            throw new AuthenticationException();
        }
    }
    
    public void register(AppUser newUser) throws InvalidRequestException, ResourcePersistenceException {
        
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid new user data provided!");
        }
        
        try {
            session.insert(newUser);
            
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
            throw new ResourcePersistenceException();
        } catch (UsernameUnavailableException | EmailUnavailableException e) {
            logger.warn(e.getMessage());
            throw new ResourcePersistenceException(e.getMessage());
        }
        
    }
    
    public void update(AppUser newUser) throws InvalidRequestException {
        
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid new user data provided!");
        }
        
        try {
            session.save(newUser);
            
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
            throw new ResourcePersistenceException();
        }
       /* catch (UsernameUnavailableException | EmailUnavailableException e) {
            logger.warn(e.getMessage());
            throw new ResourcePersistenceException(e.getMessage());
        }*/
        
    }
    
    public void delete(AppUser user) throws ResourcePersistenceException {
        
        try {
            
            session.remove(user);
            
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
            throw new ResourcePersistenceException();
        }
    }
    
    public boolean isUsernameEmailAvailable(AppUser user) {
    
        try {
            return session.isEntityUnique(user);
        } catch (SQLException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        }
    
    
        return false;
    }
    
    public boolean isUserValid(AppUser user) {
        Predicate<String> isNullOrEmpty = str -> str == null || str.trim()
                                                                   .isEmpty();
        BiPredicate<String, Integer> lengthIsInvalid = (str, length) -> str.length() > length;
        
        if (user == null) return false;
        if (isNullOrEmpty.test(user.getUsername()) || lengthIsInvalid.test(user.getUsername(), 20)) return false;
        if (isNullOrEmpty.test(user.getPassword()) || lengthIsInvalid.test(user.getPassword(), 255)) return false;
        if (isNullOrEmpty.test(user.getEmail()) || lengthIsInvalid.test(user.getEmail(), 255)) return false;
        if (isNullOrEmpty.test(user.getFirstName()) || lengthIsInvalid.test(user.getFirstName(), 25)) return false;
        if (isNullOrEmpty.test(user.getLastName()) || lengthIsInvalid.test(user.getLastName(), 25)) return false;
        return user.getAge() >= 0;
    }
    
}
