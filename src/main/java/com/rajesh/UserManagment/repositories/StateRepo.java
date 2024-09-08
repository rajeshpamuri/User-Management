package com.rajesh.UserManagment.repositories;

import com.rajesh.UserManagment.Entitys.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepo  extends JpaRepository<State,Integer> {
    List<State> findBycountryId(Integer countryId);
}
