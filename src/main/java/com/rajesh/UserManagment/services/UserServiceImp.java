package com.rajesh.UserManagment.services;

import com.rajesh.UserManagment.Dtos.LoginFormDTO;
import com.rajesh.UserManagment.Dtos.RegisterFormDTO;
import com.rajesh.UserManagment.Dtos.ResetPwdFormDTO;
import com.rajesh.UserManagment.Dtos.UserDTO;
import com.rajesh.UserManagment.Entitys.City;
import com.rajesh.UserManagment.Entitys.Country;
import com.rajesh.UserManagment.Entitys.State;
import com.rajesh.UserManagment.Entitys.User;
import com.rajesh.UserManagment.repositories.CityRepo;
import com.rajesh.UserManagment.repositories.CountryRepo;
import com.rajesh.UserManagment.repositories.StateRepo;
import com.rajesh.UserManagment.repositories.UserRepo;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.RuntimeErrorException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{



    @Autowired
    private EmailService emailService;


    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private StateRepo stateRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private UserRepo userRepo;

   private String password;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    private static final int DEFAULT_PASSWORD_LENGTH = 12;
    private final SecureRandom secureRandom = new SecureRandom();




    public String generatePassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));

        }

        return password.toString();
    }


    @Override
    public String generateRandomPassword() {
        return generatePassword(DEFAULT_PASSWORD_LENGTH);
    }



    @Override
    public Map<Integer, String> getCountries() {
        Map<Integer,String> countrymap=new HashMap<>();

        List<Country> countryList = countryRepo.findAll();
        countryList.forEach(c-> {
              countrymap.put(c.getCountryId(), c.getCountryName());
          });
        return countrymap;
    }

    @Override
    public Map<Integer, String> getStates(Integer countryId) {
        Map<Integer,String> stateMap=new HashMap<>();
        List<State> bycountryId = stateRepo.findBycountryId(countryId);
        bycountryId.forEach(s->{
            stateMap.put(s.getStateId(),s.getStateName());
        });
        return stateMap ;
    }

    @Override
    public Map<Integer, String> getCities(Integer stateId) {
        Map<Integer,String> cityMap=new HashMap<>();
        List<City> byStateId = cityRepo.findByStateId(stateId);
        byStateId.forEach(c->{
            cityMap.put(c.getCityId(), c.getCityName());
        });
        return cityMap;
    }

    @Override
    public boolean duplicateEmailCheck(String email) {
        User byEmail = userRepo.findByEmail(email);
        if (byEmail!=null){
            return true;
       }
        return false;
    }

    @Override
   public boolean saveUser(RegisterFormDTO regFormDTO) throws MessagingException {
        User user=new User();
        BeanUtils.copyProperties(regFormDTO,user);

        Country countryBYId = countryRepo.findById(regFormDTO.getCountryId()).orElse(null);
        user.setCountryName(countryBYId);

        State sityById = stateRepo.findById(regFormDTO.getStateId()).orElse(null);
        user.setState(sityById);

        City state = cityRepo.findById(regFormDTO.getCityId()).orElse(null);
        user.setCity(state);

        String string = generateRandomPassword();
        user.setPwd(string);
        user.setPassRest("No");
        User save = userRepo.save(user);

        if (save.getId()!=null){
            emailService.sendEmail("Password"," Your Account Password To Login:"+string,regFormDTO.getEmail());
            return true;
        }
        return false;

    }

        public UserDTO login(LoginFormDTO loginFormDTO) {
        User byEmail = userRepo.findByEmailAndPwd(loginFormDTO.getEmail(),loginFormDTO.getPwd());
        if (byEmail!=null){
          UserDTO userDTO=new UserDTO();
//        userDTO.setEmail(byEmail.getEmail());
//        userDTO.setPwd(byEmail.getPwd());
          BeanUtils.copyProperties(loginFormDTO,userDTO);
        return userDTO;
        }
        return null;
     }

     @Override
      public boolean resetPwd(ResetPwdFormDTO resetPwdDTO) {
        User byEmail = userRepo.findByEmail(resetPwdDTO.getEmail());
        byEmail.setPwd(resetPwdDTO.getConPwd());
        byEmail.setPassRest("Yes");
        User save = userRepo.save(byEmail );
        if (save!=null){
            return true;
        }
        return false;
    }
}
