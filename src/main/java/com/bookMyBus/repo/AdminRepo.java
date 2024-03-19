package com.bookMyBus.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookMyBus.pojo.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer>{

}
