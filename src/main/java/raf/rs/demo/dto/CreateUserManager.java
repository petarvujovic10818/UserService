package raf.rs.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter
@Setter
@Data
public class CreateUserManager {

    @NotBlank
    private String username;

    @Length(min = 8, max = 20)
    private String password;

    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private Date dateOfBirth;

    @NotBlank
    private String companyName;

    @NotBlank
    private Date dateOfWork;

    private String role;

}
