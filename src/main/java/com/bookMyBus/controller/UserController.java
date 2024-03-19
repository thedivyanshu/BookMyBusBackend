package com.bookMyBus.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookMyBus.exception.UserValidationException;
import com.bookMyBus.pojo.BookingDetails;
import com.bookMyBus.pojo.BusDetails;
import com.bookMyBus.pojo.Passenger;
import com.bookMyBus.pojo.User;
import com.bookMyBus.serviceImpl.UserServiceImpl;
import com.bookMyBus.utils.UserAuth;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl service;
	
	//********************USER*******************
	@PostMapping("/addUser")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user, Errors error) {
		if (error.hasErrors()) {
			throw new UserValidationException("invalid data provided");
		}
		User addedUser =  service.addUser(user);
		return ResponseEntity.ok().body(addedUser);
	}

	@PostMapping("/userLogin")
	public ResponseEntity<User> loginUser(@RequestBody UserAuth auth) {
		User user = service.userLogin(auth);
		return ResponseEntity.ok().body(user);
		
	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<User> getUser(@PathVariable Integer id) {
		
		User user = service.getUser(id);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/updateUser")
	public ResponseEntity<Void> updateUser(@Valid @RequestBody User user) {
		service.updateUser(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		service.deleteUser(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	//****************BOOKING***********************
	@PostMapping("/addBooking/{userId}/{busNumber}")
	public ResponseEntity<BookingDetails> addBooking(@RequestBody BookingDetails booking,@PathVariable Integer userId,@PathVariable Integer busNumber){
		BookingDetails details = service.addBooking(booking, userId, busNumber);
		return ResponseEntity.ok().body(details);
	}
	
	@DeleteMapping("/deleteBooking/{bookingId}/{userId}")
	public void deleteBooking(@PathVariable Integer bookingId,@PathVariable Integer userId) {
		service.deleteBooking(bookingId, userId);
	}
	
	@GetMapping("/getBookingByUser/{userId}")
	public ResponseEntity<List<BookingDetails>> getBookingByUser(@PathVariable Integer userId){
		List<BookingDetails> list = service.getBookingByUserId(userId);
		return ResponseEntity.ok().body(list);
	}
	//******************************BUS************************
	@GetMapping("/findBus/{arrivalBusstop}/{departureBusstop}/{date}")
	public ResponseEntity<BusDetails> findByRouteAndDate(@PathVariable String arrivalBusstop,@PathVariable String departureBusstop,@PathVariable String date){
		BusDetails details = service.findByRouteAndDate(arrivalBusstop, departureBusstop, date);
		return ResponseEntity.ok().body(details);
	}
	
	
	@GetMapping("/getBusByNumber/{busNumber}")
	public ResponseEntity<BusDetails> getBusByNumber(@PathVariable Integer busNumber){
		BusDetails details = service.getBusByBusNumber(busNumber);
		return ResponseEntity.ok().body(details);
	}
	
	//*******************PASSENGER***********************
	@PostMapping("/updatePassenger")
	public ResponseEntity<Passenger> updatePassenger(@RequestBody Passenger passenger){
		Passenger p = service.updatePassenger(passenger);
		return ResponseEntity.ok().body(p);
	}
}
