package com.rajesh.UserManagment.Entitys;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Data
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private Integer phno;
    private String pwd;
    private String passRest;
    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country countryName;
    @ManyToOne
    @JoinColumn(name = "stateId")
    private  State state;
    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;
    @CreationTimestamp
    @Column(name = "creLocal_Date",updatable = false)
    private LocalDate creLocalDate;
    @UpdateTimestamp
    @Column(name = "updated_Date",insertable = true)
    private LocalDate updatedDate;

}
