package com.bookMyBus.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookMyBus.pojo.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
