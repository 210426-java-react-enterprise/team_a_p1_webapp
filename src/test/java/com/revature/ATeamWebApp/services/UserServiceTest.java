package com.revature.ATeamWebApp.services;

import com.revature.ATeamORM.datasource.Result;
import com.revature.ATeamORM.datasource.Session;
import com.revature.ATeamORM.repos.ObjectRepo;
import com.revature.ATeamWebApp.exceptions.AuthenticationException;
import com.revature.ATeamWebApp.exceptions.ResourcePersistenceException;
import com.revature.ATeamWebApp.models.AppUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Juan Mendoza
 */
public class UserServiceTest {
    
    private UserService sut; //system under testing
    private Session mockSession;
    private List<AppUser> testUsers;
  
    
    @Before
    public void setUp(){
        testUsers = new ArrayList<>();
        testUsers.add(new AppUser(1,"t1","password","t@gmail.com","t1","t1",30));
        
        mockSession = mock(Session.class);
        sut = new UserService(mockSession);
    }
    
    @After
    public void tearDown(){
        mockSession = null;
        sut = null;
        testUsers = null;
    }
    
    @Test(expected = ResourcePersistenceException.class)
    public void test_getAllUserWithException() throws SQLException, IllegalAccessException {
        doThrow(SQLException.class).when(mockSession).findAll(any());
        
        sut.getAllUsers();
    
        verify(mockSession,times(0)).isEntityUnique(any());
        verify(mockSession,times(0)).findAll(any());
        verify(mockSession,times(0)).find(any(),any(),any());
        verify(mockSession,times(0)).save(any());
        verify(mockSession,times(0)).insert(any());
        verify(mockSession,times(0)).remove(any());
        
    }
    
    @Test
    public void test_getAllUsers() throws SQLException {
        UserService spyUS = spy(sut);
        List<AppUser> expectedList = testUsers;
        
        doReturn(expectedList).when(spyUS).getAllUsers();
        
        List<AppUser> actual = spyUS.getAllUsers();
        
        verify(spyUS,times(1)).getAllUsers();
        Assert.assertEquals(expectedList,actual);
    }
    
   @Test(expected = AuthenticationException.class)
   public void test_getUserWithException() throws SQLException, IllegalAccessException {
        doThrow(SQLException.class).when(mockSession).find(any(),any(),any());
        
        sut.getUser("username",testUsers.get(0).getUsername());
    
        verify(mockSession,times(0)).isEntityUnique(any());
        verify(mockSession,times(0)).findAll(any());
        verify(mockSession,times(0)).find(any(),any(),any());
        verify(mockSession,times(0)).save(any());
        verify(mockSession,times(0)).insert(any());
        verify(mockSession,times(0)).remove(any());
    }
    
    @Test
    public void test_getUser() throws SQLException {
        UserService spyUS = spy(sut);
        AppUser expected = testUsers.get(0);
    
        doReturn(expected).when(spyUS).getUser(any(),any());
    
        AppUser actual = spyUS.getUser(any(),any());
    
        verify(spyUS,times(1)).getUser(any(),any());
        Assert.assertEquals(expected,actual);
    }
    
    @Test (expected = AuthenticationException.class)
    public void test_authenticateWithException() throws SQLException, IllegalAccessException {
        doThrow(SQLException.class).when(mockSession).find(any(),any(),any());
        
        sut.authenticate("username",testUsers.get(0).getUsername());
    
        verify(mockSession,times(0)).isEntityUnique(any());
        verify(mockSession,times(0)).findAll(any());
        verify(mockSession,times(0)).find(any(),any(),any());
        verify(mockSession,times(0)).save(any());
        verify(mockSession,times(0)).insert(any());
        verify(mockSession,times(0)).remove(any());
        
    }
    
    @Test
    public void test_authenticateWithIncorrectUsernameAndPassword() throws SQLException {
        UserService spyUS = spy(sut);
        AppUser expected = testUsers.get(0);
    
        doReturn(expected).when(spyUS).authenticate(any(),any());
    
        AppUser actual = spyUS.authenticate(any(),any());
    
        verify(spyUS,times(1)).authenticate(any(),any());
        Assert.assertEquals(expected,actual);
    }
    
    @Test
    public void test_isUsernameEmailAvailableWithFalseCredentials() throws SQLException, IllegalAccessException {
        when(mockSession.isEntityUnique(any())).thenReturn(false);
        
        sut.isUsernameEmailAvailable(testUsers.get(0));
        
        verify(mockSession,times(1)).isEntityUnique(any());
        verify(mockSession,times(0)).findAll(any());
        verify(mockSession,times(0)).find(any(),any(),any());
        verify(mockSession,times(0)).save(any());
        verify(mockSession,times(0)).insert(any());
        verify(mockSession,times(0)).remove(any());
    }
    
    @Test
    public void test_isUsernameEmailAvailableWithValidCredentials() throws SQLException, IllegalAccessException {
        when(mockSession.isEntityUnique(any())).thenReturn(true);
        
        sut.isUsernameEmailAvailable(testUsers.get(0));
        
        verify(mockSession,times(1)).isEntityUnique(any());
        verify(mockSession,times(0)).findAll(any());
        verify(mockSession,times(0)).find(any(),any(),any());
        verify(mockSession,times(0)).save(any());
        verify(mockSession,times(0)).insert(any());
        verify(mockSession,times(0)).remove(any());
    
    }
    
    @Test(expected = ResourcePersistenceException.class)
    public void test_isUsernameEmailAvailableWithException() throws SQLException, IllegalAccessException {
        doThrow(SQLException.class).when(mockSession).isEntityUnique(any());
        
        sut.isUsernameEmailAvailable(testUsers.get(0));
        
        verify(mockSession,times(0)).isEntityUnique(any());
        verify(mockSession,times(0)).findAll(any());
        verify(mockSession,times(0)).find(any(),any(),any());
        verify(mockSession,times(0)).save(any());
        verify(mockSession,times(0)).insert(any());
        verify(mockSession,times(0)).remove(any());
        
    }
    
    @Test
    public void test_registerProperUser() throws SQLException {
        sut.register(testUsers.get(0));
        
        verify(mockSession,times(1)).insert(any());
        verify(mockSession,times(0)).findAll(any());
        
        try{
            verify(mockSession,times(0)).find(any(),any(),any());
            verify(mockSession,times(0)).isEntityUnique(any());
            verify(mockSession,times(0)).save(any());
            verify(mockSession,times(0)).remove(any());
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
    }
    
    @Test(expected = ResourcePersistenceException.class)
    public void test_registerSQLException() throws SQLException {
        doThrow(SQLException.class).when(mockSession).insert(any());
        sut.register(testUsers.get(0));
        
        verify(mockSession,times(0)).insert(any());
        verify(mockSession,times(0)).findAll(any());
        
        try{
            verify(mockSession,times(0)).find(any(),any(),any());
            verify(mockSession,times(0)).isEntityUnique(any());
            verify(mockSession,times(0)).save(any());
            verify(mockSession,times(0)).remove(any());
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
    }
    
    @Test
    public void test_updateProperUser() throws SQLException {
        sut.update(testUsers.get(0));
    
        verify(mockSession,times(1)).save(any());
        verify(mockSession,times(0)).findAll(any());
        
        try{
            verify(mockSession,times(0)).find(any(),any(),any());
            verify(mockSession,times(0)).isEntityUnique(any());
            verify(mockSession,times(0)).insert(any());
            verify(mockSession,times(0)).remove(any());
    
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    
    }
    
    @Test(expected = ResourcePersistenceException.class)
    public void test_updateSQLException() throws SQLException {
        
        doThrow(SQLException.class).when(mockSession).save(any());
        
        sut.update(testUsers.get(0));
        
        verify(mockSession,times(0)).save(any());
        verify(mockSession,times(0)).findAll(any());
        
        try{
            verify(mockSession,times(0)).find(any(),any(),any());
            verify(mockSession,times(0)).isEntityUnique(any());
            verify(mockSession,times(0)).insert(any());
            verify(mockSession,times(0)).remove(any());
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
    }
    
    @Test(expected = ResourcePersistenceException.class)
    public void test_deleteWithSQLException() throws SQLException {
        //we throw resource persistence exception when sql exception occurs
       doThrow(SQLException.class).when(mockSession).remove(any());
       
        sut.delete(testUsers.get(0));
        
        verify(mockSession,times(0)).remove(any());
        verify(mockSession,times(0)).findAll(any());
        verify(mockSession,times(0)).find(any(),any(),any());
        verify(mockSession,times(0)).save(any());
        verify(mockSession,times(0)).insert(any());
        verify(mockSession,times(0)).isEntityUnique(any());
    }
    
  
    
    @Test
    public void test_delete() throws SQLException {
  
        sut.delete(testUsers.get(0));
    
        verify(mockSession,times(1)).remove(any());
        verify(mockSession,times(0)).findAll(any());
        verify(mockSession,times(0)).find(any(),any(),any());
        verify(mockSession,times(0)).save(any());
        verify(mockSession,times(0)).insert(any());
        verify(mockSession,times(0)).isEntityUnique(any());
    }
    
    @Test
    public void test_isUserValidWithValidUserName() throws NoSuchMethodException, InvocationTargetException {
        AppUser validUser = new AppUser(1,"TestUsername","password","test@gmail.com","jihn","bard",30);
        testUsers.add(validUser);//well be position one because in setUp method we have a user added already
        Boolean expected = true;
        
        Boolean actual = sut.isUserValid(validUser);
        
        Assert.assertTrue(actual);
        
    }
    @Test
    public void test_isUserValidWithEmptyUserName() throws NoSuchMethodException, InvocationTargetException {
        AppUser validUser = new AppUser(1,"","password","test@gmail.com","jihn","bard",30);
        testUsers.add(validUser);//well be position one because in setUp method we have a user added already
        Boolean expected = false;
        
        Boolean actual = sut.isUserValid(validUser);
        
        Assert.assertFalse(actual);
        
    }
    
    @Test
    public void test_isUserValidWithNullUsername() throws NoSuchMethodException, InvocationTargetException {
        AppUser validUser = new AppUser(1,null,"password","test@gmail.com","jihn","bard",30);
        testUsers.add(validUser);//well be position one because in setUp method we have a user added already
        Boolean expected = true;
        
        Boolean actual = sut.isUserValid(validUser);
        
        Assert.assertFalse(actual);
        
    }
}
