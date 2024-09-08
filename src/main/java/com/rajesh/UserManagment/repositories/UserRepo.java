package com.rajesh.UserManagment.repositories;

import com.rajesh.UserManagment.Entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepo extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    User findByEmailAndPwd(String email, String pwd);
}
