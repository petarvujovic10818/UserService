package raf.rs.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import raf.rs.demo.model.Role;

import java.util.Date;

@Data
@Getter
@Setter
public class UserDto {

    private Long id;

    private String email;

    private String phoneNumber;

    private String name;

    private String surname;

    private Date dateOfBirth;

    private String passportNumber;

    private Integer daysOfRenting;

    private String companyName;

    private Date dateOfWork;

    private boolean active;

    private String rank;

    private Role role;
}
