package raf.rs.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private Date dateOfBirth;

    private String passportNumber; //client

    private Integer daysOfRenting; //client

    private String companyName; //manager

    private Date dateOfWork; //manager

    private boolean active = false;

    private String rank;

    @ManyToOne(optional = false)
    private Role role;
}
