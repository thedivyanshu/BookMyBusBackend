package com.bookMyBus.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookMyBus.exception.BusDetailsNotFoundException;
import com.bookMyBus.exception.NullBusDetailsException;
import com.bookMyBus.exception.NullUserException;
import com.bookMyBus.exception.PassengerNotFoundException;
import com.bookMyBus.exception.UserAlreadyExistException;
import com.bookMyBus.exception.UserDoesnotExistException;
import com.bookMyBus.pojo.BookingDetails;
import com.bookMyBus.pojo.BusDetails;
import com.bookMyBus.pojo.Passenger;
import com.bookMyBus.pojo.User;
import com.bookMyBus.repo.BookingDetailsRepo;
import com.bookMyBus.repo.BusDetailsRepo;
import com.bookMyBus.repo.PassengerRepo;
import com.bookMyBus.repo.UserRepo;
import com.bookMyBus.service.UserService;
import com.bookMyBus.utils.UserAuth;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepo userRepo;

	@Autowired
	BusDetailsRepo busRepo;

	@Autowired
	BookingDetailsRepo bookingRepo;
	
	@Autowired
	PassengerRepo passengerRepo;
	
	//*******************USER*********************
	@Override
	public User addUser(User user) {

		if (user == null)
			throw new NullUserException("Please Enter Data");
		Integer userId = (int) ((Math.random() * 900) + 100);
		user.setUserId(userId);
		Optional<User> checkUser = userRepo.findById(user.getUserId());
		if (checkUser.isPresent())
			throw new UserAlreadyExistException("user already exists");

		userRepo.save(user);
		System.out.println("user Added Succesfully");
		return user;

	}

	@Override
	public User updateUser(User user) {
	    if (user == null) {
	        throw new NullUserException("Please Enter Data");
	    }

	    Optional<User> checkUser = userRepo.findById(user.getUserId());
	    if (checkUser.isPresent()) {
	        return userRepo.save(user);
	    } else {
	        throw new UserDoesnotExistException("Sorry! User not found");
	    }
	}

	
	@Override
	public User getUser(Integer userId) {
	    if (userId == null)
	        throw new NullUserException("Please Enter Data");

	    Optional<User> userOptional = userRepo.findById(userId);

	    if (userOptional.isEmpty()) {
	        // Throw NullUserException when the user is not found
	        throw new NullUserException("User not found with ID: " + userId);
	    }

	    return userOptional.get();
	}

	
	@Override
	public void deleteUser(Integer userId) {
		if (userId == null)
			throw new NullUserException("Please Enter Data");
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent())
			throw new UserDoesnotExistException("Sorry! User not found");
		userRepo.deleteById(userId);
	}

	
	@Override
	public User userLogin(UserAuth auth) {
		if (auth == null) {
			throw new NullUserException("Please Enter Data");
		}
		Optional<User> user = userRepo.findById(auth.getUserId());
		if (user.isPresent()) {
			if (user.get().getUserId() == auth.getUserId() && user.get().getPassword().equals(auth.getPassword())) {
				return user.get();
			} else {
				throw new UserDoesnotExistException("Invalid login ID or Password");
			}
			
		} else {
			throw new UserDoesnotExistException("Sorry! User not found");
		}
	}

	//***********************BOOKING************************
	@Override
	public BookingDetails addBooking(BookingDetails booking, Integer userId, Integer busNumber) {
		Optional<User> user = userRepo.findById(userId);
		Optional<BusDetails> bus = busRepo.findById(busNumber);
		if (!user.isPresent()) {
			throw new UserDoesnotExistException("Sorry! user id not found");
		}
		if (!bus.isPresent()) {
			throw new BusDetailsNotFoundException("Oops!! bus details not found");
		}
		Integer bookingId = (int) ((Math.random() * 9000) + 1000);
		booking.setBookingId(bookingId);
		booking.setOwnerId(userId);
		booking.setBusNumber(busNumber);
		booking.setBookingDate(LocalDate.now().toString());
		booking.setBookingTime(LocalTime.now().toString().substring(0, 5));
		booking.setTotalCost(bus.get().getCost() * booking.getPassengers().size());
		List<BookingDetails> bookingList = user.get().getBookingDetails();
		bookingList.add(booking);
		user.get().setBookingDetails(bookingList);
		updateUser(user.get());
		return bookingRepo.getById(bookingId);
	}
     
	
	//Deleting Booking
	@Override
	public void deleteBooking(Integer bookingId, Integer userId) {
		Optional<User> u = userRepo.findById(userId);
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
				System.out.println("Sorry! booking id found");
				deleteBooking = b;
			}
		}
		bookingList.remove(deleteBooking);
		user.setBookingDetails(bookingList);
		bookingRepo.deleteById(bookingId);
		updateUser(user);
	}

	
	//List all the booking details made by the user
	@Override
	public List<BookingDetails> getBookingByUserId(Integer userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			throw new UserDoesnotExistException("Oops! user id not found");
		}
		return user.get().getBookingDetails();
	}
	
	
	//***************BUS****************

	  //Searching bus details //checking the findByRouteAndDate
	@Override
	public BusDetails findByRouteAndDate(String arrivalBusStop, String departureBusStop, String date) {
		List<BusDetails> list = busRepo.findByRouteDate(arrivalBusStop.toLowerCase(),
				departureBusStop.toLowerCase());
		for (BusDetails f : list) {
			if (f.getDepartureDate().equals(date)) {
				return f;
			}
		}
		throw new BusDetailsNotFoundException("Sorry! details not found");
	}
    
	
	@Override
	public BusDetails getBusByBusNumber(Integer busNumber) {
		if (busNumber == null) {
			throw new NullBusDetailsException("Please Enter Data");
		}
		Optional<BusDetails> details = busRepo.findById(busNumber);
		if (!details.isPresent()) {
			throw new BusDetailsNotFoundException("Oops! No Bus Service Found");
		}
		return details.get();
	}
	
	//*********************PASSENGER***************************
	@Override
	public Passenger updatePassenger(Passenger passenger) {
		if (passenger == null) {
			throw new PassengerNotFoundException("Please Enter Data");
		}
		
		Optional<Passenger> oldPassenger = passengerRepo.findById(passenger.getPassengerId()); 
		if (!oldPassenger.isPresent()) {
			throw new PassengerNotFoundException("Sorry! No Passenger Is Present With This Id Number");
		}
		passengerRepo.save(passenger);
		return passenger;
	}

}
