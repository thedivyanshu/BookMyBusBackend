package com.bookMyBus.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookMyBus.pojo.BookingDetails;

public interface BookingDetailsRepo extends JpaRepository<BookingDetails, Integer> {

}
