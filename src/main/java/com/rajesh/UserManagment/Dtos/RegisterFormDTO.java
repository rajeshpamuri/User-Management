package com.rajesh.UserManagment.Dtos;

import lombok.Data;

@Data
public class RegisterFormDTO {

    //private Integer id;
    private String name;
    private String email;
    private Integer phno;
    //private String pwd;
    //private String passRest;
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;

}
