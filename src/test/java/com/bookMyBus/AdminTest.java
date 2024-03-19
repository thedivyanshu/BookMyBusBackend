package com.bookMyBus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookMyBus.pojo.Admin;
import com.bookMyBus.repo.AdminRepo;
import com.bookMyBus.repo.BookingDetailsRepo;
import com.bookMyBus.repo.BusDetailsRepo;
import com.bookMyBus.repo.PassengerRepo;
import com.bookMyBus.repo.UserRepo;
import com.bookMyBus.serviceImpl.AdminServiceImpl;


@SpringBootTest
public class AdminTest {

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private BusDetailsRepo busDetailsRepo;

    @Mock
    private PassengerRepo passengerRepo;

    @Mock
    private BookingDetailsRepo bookingRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private AdminServiceImpl adminService;

    public AdminTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for addAdmin method
    @Test
    public void testAddAdmin() {
        Admin admin = new Admin(1, "password", "AdminName");

        when(adminRepo.findById(admin.getAdminId())).thenReturn(Optional.empty());
        when(adminRepo.save(admin)).thenReturn(admin);

        Admin result = adminService.addAdmin(admin);

        assertNotNull(result);
        assertEquals(admin.getAdminId(), result.getAdminId());
        assertEquals(admin.getAdminName(), result.getAdminName());
        assertEquals(admin.getPassword(), result.getPassword());

        verify(adminRepo, times(1)).findById(admin.getAdminId());
        verify(adminRepo, times(1)).save(admin);
    }
 // Test for getAdmin method
    @Test
    public void testGetAdmin() {
        Admin admin = new Admin(1, "password", "AdminName");

        when(adminRepo.findById(admin.getAdminId())).thenReturn(Optional.of(admin));

        Admin result = adminService.getAdmin(admin.getAdminId());

        assertNotNull(result);
        assertEquals(admin.getAdminId(), result.getAdminId());
        assertEquals(admin.getAdminName(), result.getAdminName());
        assertEquals(admin.getPassword(), result.getPassword());

        verify(adminRepo, times(1)).findById(admin.getAdminId());
    }

    
    // Test for deleteAdmin method
    @Test
    public void testDeleteAdmin() {
        Admin admin = new Admin(1, "password", "AdminName");

        when(adminRepo.findById(admin.getAdminId())).thenReturn(Optional.of(admin));

        adminService.deleteAdmin(admin.getAdminId());

        verify(adminRepo, times(1)).findById(admin.getAdminId());
        verify(adminRepo, times(1)).deleteById(admin.getAdminId());
    }
    
 
}

