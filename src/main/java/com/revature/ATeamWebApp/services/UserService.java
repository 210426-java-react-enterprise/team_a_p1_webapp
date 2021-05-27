package com.revature.ATeamWebApp.services;

import com.revature.ATeamORM.util.datasource.ConnectionFactory;
import com.revature.ATeamWebApp.exceptions.*;
import com.revature.ATeamWebApp.models.AppUser;
import com.revature.ATeamWebApp.util.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class UserService {

    private Logger logger = Logger.getLogger();
    

    public UserService() {
    
    }

    public List<AppUser> getAllUsers() {
        try (Connection conn =ConnectionFactory.getInstance().getConnection()) {
            return userDao.findAllUsers(conn);
        }  catch (SQLException | DataSourceException e) {
            logger.warn(e.getMessage());
            throw new AuthenticationException();
        }

    }
    /*
     
     */
    public AppUser authenticate(String username, String password) throws AuthenticationException {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            return userDao.findUserByUsernameAndPassword(conn, username, password)
                                      .orElseThrow(AuthenticationException::new);

        } catch (SQLException | DataSourceException e) {
            logger.warn(e.getMessage());
            throw new AuthenticationException();
        }

    }

    public void register(AppUser newUser) throws InvalidRequestException, ResourcePersistenceException {
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid new user data provided!");
        }
        
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
    
          /*  PreparedStatement pstmt = conn.
            ObjectRepo or = new ObjectRepo();
            //some logic to check if user is available
            or.create(conn,newUser,);
            */
            
            conn.commit();

        } catch (SQLException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
            throw new ResourcePersistenceException();
        } catch (UsernameUnavailableException | EmailUnavailableException e) {
            logger.warn(e.getMessage());
            throw new ResourcePersistenceException(e.getMessage());
        }


    }

    private boolean isUserValid(AppUser user) {
        Predicate<String> isNullOrEmpty = str -> str == null || str.trim().isEmpty();
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
