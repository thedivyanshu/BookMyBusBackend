package com.bookMyBus.serviceImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookMyBus.exception.AdminAlreadyExistException;
import com.bookMyBus.exception.AdminDoesnotExistException;
import com.bookMyBus.exception.BookingDoesNotFoundException;
import com.bookMyBus.exception.BusDetailsNotFoundException;
import com.bookMyBus.exception.NullAdminException;
import com.bookMyBus.exception.NullBusDetailsException;
import com.bookMyBus.exception.NullUserException;
import com.bookMyBus.exception.UserDoesnotExistException;
import com.bookMyBus.pojo.Admin;
import com.bookMyBus.pojo.BookingDetails;
import com.bookMyBus.pojo.BusDetails;
import com.bookMyBus.pojo.Passenger;
import com.bookMyBus.pojo.User;
import com.bookMyBus.repo.*;
import com.bookMyBus.service.AdminService;
import com.bookMyBus.utils.AdminAuth;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepo adminRepo;

	@Autowired
	BusDetailsRepo busRepo;
	
	@Autowired
	PassengerRepo passengerRepo;
	
	@Autowired
	BookingDetailsRepo bookingRepo;
	
	@Autowired
	UserRepo userDao;
	//*****************************ADMIN***************************
	//adding admin to the database
	@Override
	public Admin addAdmin(Admin admin) {
		if (admin == null)
			throw new NullAdminException("Please Enter Data");
		Integer adminId = (int) ((Math.random() * 900) + 100); //
		
		admin.setAdminId(adminId);
		Optional<Admin> checkAdmin = adminRepo.findById(admin.getAdminId());
		if (checkAdmin.isPresent()) {
			throw new AdminAlreadyExistException("admin already exist exception");
		} else {
			adminRepo.save(admin);
			System.out.println(adminId);
			return admin;
		}
	}
	
	
//for getting admin by ID
	@Override
	public Admin getAdmin(Integer adminId) {
		if (adminId == null)
			throw new NullAdminException("Please Enter Data");
		Optional<Admin> admin = adminRepo.findById(adminId);
		if (!admin.isPresent()) {
			throw new AdminDoesnotExistException("admin does not exist ");
		}
		return admin.get();
	}
	
	//FOR DELETING ADMIN
	@Override
	public void deleteAdmin(Integer adminId) {
		if (adminId == null)
			throw new NullAdminException("Please Enter Data");
		Optional<Admin> admin = adminRepo.findById(adminId);
		if (!admin.isPresent()) {
			throw new AdminDoesnotExistException("admin Doesnot Exist Exception");
		}
		adminRepo.deleteById(adminId);
	}
    
	//admin Login 
	@Override
	public Admin adminLogin(AdminAuth auth) {
		if (auth == null) {
			throw new NullAdminException("Please Enter Data");
		}
		Optional<Admin> admin = adminRepo.findById(auth.getAdminId());
		if (admin.isPresent()) {
			
			///////Check
			if (admin.get().getAdminId() == auth.getAdminId() && admin.get().getPassword().equals(auth.getPassword())) {
				return admin.get();
			} else {
				throw new AdminDoesnotExistException("Invalid Login ID or Password");
			}
			
		} else
			throw new AdminDoesnotExistException("Please Enter Data");
	}
     
	//*******************BUS***********************
	//For getting all the bus details
	@Override
	public List<BusDetails> getAllBusDetails() {
		return busRepo.findAll();
	}
    
	//For adding bus details
	@Override
	public BusDetails addBusDetails(BusDetails details) {
		if (details == null) {
			throw new NullBusDetailsException("Please Enter Data");
		}
		Integer busNumber = (int) ((Math.random() * 9000) + 1000);
		details.setBusNumber(busNumber);
		busRepo.save(details);
		return details;
	}

	//Deleting Bus By ID
	@Override
	public void deleteBus(Integer busNumber) {
		if (busNumber == null)
			throw new NullBusDetailsException("Please Enter Data");
		Optional<BusDetails> details = busRepo.findById(busNumber);
		if (!details.isPresent()) {
			throw new BusDetailsNotFoundException("Bus Details Not Found");
		}
		busRepo.deleteById(busNumber);
	}
    
	//Updating the bus details By ID
	@Override
	public BusDetails updateBus(BusDetails details) {
		if (details == null)
			throw new NullBusDetailsException("Please Enter Data");
		Optional<BusDetails> busDetails = busRepo.findById(details.getBusNumber());
		if (!busDetails.isPresent()) {
			throw new BusDetailsNotFoundException("Bus with busNumber: " + details.getBusNumber() + " not exists..");
		}
		busRepo.save(details);
		return details;
	}
	
	//************************PASSENGER**********************
	//Getting all the passengers
	public List<Passenger> getAllPassengers(){
		return passengerRepo.findAll();
	}
	
	//Getting list of passengers by ID
	public List<Passenger> getPassengersByBooking(Integer id){
		if (id == null) throw new BookingDoesNotFoundException("Please Enter Data");
		Optional<BookingDetails> details = bookingRepo.findById(id);
		if (!details.isPresent())
			throw new BookingDoesNotFoundException("Booking Not Found");
		return details.get().getPassengers();
	}
	//***********************USER**************************
	//Getting all the Users
		public  List<User> getAllUsers(){
			return userDao.findAll();
		}


		public void deleteUser(Integer id) {
	        if (id == null)
	            throw new IllegalArgumentException("Please Enter Data");

	        Optional<User> user = userDao.findById(id);
	        if (!user.isPresent()) {
	            throw new UserDoesnotExistException("User Does not Exist");
	        }

	        userDao.deleteById(id);
	    }
		
		@Override
		public void updateUser(User user) {
			if (user == null)
				throw new NullUserException("Please Enter Data");
			Optional<User> checkUser = userDao.findById(user.getUserId());
			if (checkUser.isPresent())
				userDao.save(user);
			else
				throw new UserDoesnotExistException("Sorry! User not found");

		}

		//**************************BOOKING**************************
		public List<BookingDetails> getBookingByUserId(Integer userId) {
			Optional<User> user = userDao.findById(userId);
			if (!user.isPresent()) {
				throw new UserDoesnotExistException("Oops! user id not found");
			}
			return user.get().getBookingDetails();
		}


		public void deleteBooking(Integer bookingId, Integer userId) {
			Optional<User> u = userDao.findById(userId);
			Optional<BookingDetails> bd = bookingRepo.findById(bookingId);
			if (!bd.isPresent()) {
				throw new UserDoesnotExistException("Sorry! booking not found");
			}
			if (!u.isPresent()) {
				throw new UserDoesnotExistException("Oops! user id not found");
			}
			User user = u.get();
			List<BookingDetails> bookingList = user.getBookingDetails();
			BookingDetails deleteBooking = null;
			for (BookingDetails b : bookingList) {
				if (b.getBookingId() == bookingId) {
					System.out.println(" booking id found");
					deleteBooking = b;
				}
			}
			bookingList.remove(deleteBooking);
			user.setBookingDetails(bookingList);
			bookingRepo.deleteById(bookingId);
			updateUser(user);
			
		}
}
