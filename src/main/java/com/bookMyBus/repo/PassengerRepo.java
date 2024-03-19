package com.bookMyBus.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookMyBus.pojo.Passenger;

public interface PassengerRepo extends JpaRepository<Passenger, Integer> {

}
