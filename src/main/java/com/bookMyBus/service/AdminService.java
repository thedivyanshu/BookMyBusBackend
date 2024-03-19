package com.bookMyBus.service;

import java.util.List;

import com.bookMyBus.pojo.Admin;
import com.bookMyBus.pojo.BusDetails;
import com.bookMyBus.pojo.Passenger;
import com.bookMyBus.pojo.User;
import com.bookMyBus.utils.AdminAuth;

public interface AdminService {
	public Admin addAdmin(Admin admin); //adding admin

	public Admin getAdmin(Integer adminId);

	public void deleteAdmin(Integer adminId);

	public Admin adminLogin(AdminAuth auth);

	public List<BusDetails> getAllBusDetails();

	public BusDetails addBusDetails(BusDetails details);

	public void deleteBus(Integer busNumber);

	public BusDetails updateBus(BusDetails details);
	
	public List<Passenger> getAllPassengers();
	
	public List<Passenger> getPassengersByBooking(Integer id);
	
	public List<User> getAllUsers();

	public void updateUser(User user);
	
	

	

}
