package com.bookMyBus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookMyBus.exception.UserDoesnotExistException;
import com.bookMyBus.pojo.User;
import com.bookMyBus.repo.UserRepo;
import com.bookMyBus.serviceImpl.UserServiceImpl;
import com.bookMyBus.utils.UserAuth;



@SpringBootTest
public class UserTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    public UserTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for addUser method
    @Test
    public void testAddUser() {
        User user = new User(/* provide necessary details */);

        when(userRepo.findById(user.getUserId())).thenReturn(Optional.empty());
        when(userRepo.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());

        verify(userRepo, times(1)).findById(user.getUserId());
        verify(userRepo, times(1)).save(user);
    }

    // Test for updateUser method
    @Test
    public void testUpdateUser() {
        User user = new User(/* provide necessary details */);

        when(userRepo.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user);

        User result = userService.updateUser(user);

        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());

        verify(userRepo, times(1)).findById(user.getUserId());
        verify(userRepo, times(1)).save(user);
    }

    // Test for getUser method
   


    @Test
    public void testGetUser() {
        Integer userId = 920;
        User user = new User(/* provide necessary details for an existing user */);

        // Assume userDao.findById(userId) returns the user
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        // Now, invoke the getUser method
        User result = userService.getUser(userId);

        // Assert that the result is not null and is the same user instance
        assertNotNull(result);
        assertEquals(user, result);

        // Verify that findById was called
        verify(userRepo, times(1)).findById(userId);
    }

    


    
    @Test
    public void testUserLoginNotFound() {
        UserAuth auth = new UserAuth(/* provide necessary details for a non-existing user */);

        when(userRepo.findById(auth.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserDoesnotExistException.class, () -> userService.userLogin(auth));

        verify(userRepo, times(1)).findById(auth.getUserId());
    }

    // Add more test cases for other methods if needed
}
