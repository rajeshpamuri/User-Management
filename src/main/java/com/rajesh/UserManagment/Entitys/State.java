package com.rajesh.UserManagment.Entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId;
    private String stateName;
    private Integer countryId;

}
