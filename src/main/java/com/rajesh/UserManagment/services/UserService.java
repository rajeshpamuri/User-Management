package com.rajesh.UserManagment.services;


import com.rajesh.UserManagment.Dtos.LoginFormDTO;
import com.rajesh.UserManagment.Dtos.RegisterFormDTO;
import com.rajesh.UserManagment.Dtos.ResetPwdFormDTO;
import com.rajesh.UserManagment.Dtos.UserDTO;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface UserService {
    String generateRandomPassword();

    public Map<Integer,String> getCountries();

public Map<Integer,String> getStates(Integer countryId);

  public Map<Integer,String> getCities(Integer stateId);

  public boolean duplicateEmailCheck(String email);

   public boolean saveUser(RegisterFormDTO regFormDTO) throws MessagingException;

 public UserDTO login(LoginFormDTO loginFormDTO);

  public boolean resetPwd(ResetPwdFormDTO resetPwdDTO);
}
