package com.rajesh.UserManagment.repositories;

import com.rajesh.UserManagment.Entitys.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country,Integer> {
    
}
