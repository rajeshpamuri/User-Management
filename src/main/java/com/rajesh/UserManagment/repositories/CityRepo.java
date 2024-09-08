package com.rajesh.UserManagment.repositories;

import com.rajesh.UserManagment.Entitys.City;
import com.rajesh.UserManagment.Entitys.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepo extends JpaRepository<City,Integer> {
    List<City> findByStateId(Integer stateId);
}
