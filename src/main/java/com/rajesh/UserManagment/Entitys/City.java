package com.rajesh.UserManagment.Entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class City {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cityId;
    private String cityName;
    private Integer stateId;
}
