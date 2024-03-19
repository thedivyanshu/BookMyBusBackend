package com.bookMyBus.service;

import java.util.List;

import com.bookMyBus.pojo.BookingDetails;
import com.bookMyBus.pojo.BusDetails;
import com.bookMyBus.pojo.Passenger;
import com.bookMyBus.pojo.User;
import com.bookMyBus.utils.UserAuth;

public interface UserService {
	public User addUser(User user);

	public User updateUser(User user);

	public User getUser(Integer userId);

	public void deleteUser(Integer userId);

	public User userLogin(UserAuth auth);

	public BookingDetails addBooking(BookingDetails booking, Integer userId, Integer busNumber);

	public void deleteBooking(Integer bookingId, Integer userId);

	public List<BookingDetails> getBookingByUserId(Integer userId);

	public BusDetails findByRouteAndDate(String arrivalBusstop, String departureBusstop, String date);
	
	public BusDetails getBusByBusNumber(Integer busNumber);
	
	public Passenger updatePassenger(Passenger passenger);

}
