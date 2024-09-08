package com.rajesh.UserManagment.Dtos;

import lombok.Data;

@Data
public class ResetPwdFormDTO {
    private String email;
    private String oldpwd ;
    private String newPwd;
    private String conPwd;
}
