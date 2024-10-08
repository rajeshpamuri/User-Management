package com.rajesh.UserManagment.controllers;

import com.rajesh.UserManagment.Dtos.*;

import com.rajesh.UserManagment.Entitys.User;
import com.rajesh.UserManagment.repositories.UserRepo;
import com.rajesh.UserManagment.services.DashService;

import com.rajesh.UserManagment.services.UserService;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private DashService dashService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/registerForm")
    public String loadRegiserPage(Model model) {
        RegisterFormDTO registerFormDTO = new RegisterFormDTO();
        Map<Integer, String> countries = userService.getCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("registerForm", registerFormDTO);
        return "register";
    }

    @GetMapping("/states/{countryId}")
    @ResponseBody
    public Map<Integer, String> getStates(@PathVariable Integer countryId) {
        Map<Integer, String> states = userService.getStates(countryId);
        return states;
    }

    @GetMapping("/cities/{stateId}")
    @ResponseBody
    public Map<Integer, String> getCities(@PathVariable Integer stateId) {
        Map<Integer, String> cities = userService.getCities(stateId);
        return cities;
    }

    @PostMapping("/register")
    public String handelForm(@ModelAttribute("registerForm") RegisterFormDTO registerFormDTO, Model model) throws MessagingException {
        boolean status = userService.duplicateEmailCheck(registerFormDTO.getEmail());
        if (status) {
            model.addAttribute("emsg", "Duplicate Email Found");

        } else {
            boolean saveUser = userService.saveUser(registerFormDTO);
            if (saveUser) {
                model.addAttribute("smsg", "Register Successes And Check Email to get Password");

            } else {
                model.addAttribute("emsg", "Registration Failed ");
            }

        }
        Map<Integer, String> countries = userService.getCountries();
        model.addAttribute("countries", countries);
        return "register";

    }

    @GetMapping("/")
    public String getLoginForm(Model model) throws MessagingException {
        LoginFormDTO loginFormDTO = new LoginFormDTO();
        model.addAttribute("loginForm", loginFormDTO);
        return "login";
    }

    @PostMapping("/login")
    public String loginValidity(@ModelAttribute("loginForm") LoginFormDTO loginFormDTO, Model model) {
        User byEmail = userRepo.findByEmail(loginFormDTO.getEmail());
        UserDTO login = userService.login(loginFormDTO);
        if (login == null) {
            model.addAttribute("emsg", "Invalid credential");
        } else {
            if ("Yes".equals(byEmail.getPassRest())) {
                return "redirect:dashboard";
            } else {
                return "redirect:resetpwd?email=" + login.getEmail();
            }
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashBoar(Model model) {
        QuoteApiResponseDTO quote = dashService.getQuote();
        model.addAttribute("quote", quote);
        return "dashboard";
    }

    @GetMapping("/resetpwd")
    public String restPwd(@RequestParam String email, Model model) {
        ResetPwdFormDTO resetPwdFormDTO = new ResetPwdFormDTO();
        model.addAttribute("resetPwdForm", resetPwdFormDTO);
        resetPwdFormDTO.setEmail(email);
        return "resetPwd";
    }

    @PostMapping("/resetPage")
    public String handelRestPed(@ModelAttribute("resetPwdForm") ResetPwdFormDTO resetPwdFormDTO, Model model) {
        User byEmail = userRepo.findByEmail(resetPwdFormDTO.getEmail());
        //Without Validating New Password And Confirm Password
//        boolean b = userService.resetPwd(resetPwdFormDTO);
//
//        if (b) {
//            return "redirect:dashboard";
//        }
//        return"resetPwd";
        //below code Validate The New Password And Confirm Password

        if (resetPwdFormDTO.getEmail().equals(byEmail.getEmail()) && resetPwdFormDTO.getNewPwd().equals(resetPwdFormDTO.getConPwd())) {
            // model.addAttribute("emsg", "Email Or Password Didn't Match");
            boolean b = userService.resetPwd(resetPwdFormDTO);
            return "redirect:dashboard";
        } else {
            // boolean b = userService.resetPwd(resetPwdFormDTO);
            model.addAttribute("emsg", "Email Or Password Didn't Match");
            return"resetpwd";
        }
    }
}






