package com.rajesh.UserManagment.Entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;
    private String countryName;


}
