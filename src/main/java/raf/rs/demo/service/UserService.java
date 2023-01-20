package raf.rs.demo.service;

import org.springframework.data.domain.Page;
import raf.rs.demo.dto.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    UserDto addClient(CreateUserClient clientDto);

    UserDto addManager(CreateUserManager managerDto);

    UserDto updateClient(Long id, CreateUserClient clientDto);

    UserDto updateManager(Long id, CreateUserManager managerDto);

    UserDto sendMailForUser(String username);

    void changePassword(Long id, String password);

    void setActive(Long id);

    void setInactive(Long id);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

}
