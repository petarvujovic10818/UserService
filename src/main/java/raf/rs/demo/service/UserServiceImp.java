package raf.rs.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import raf.rs.demo.dto.*;
import raf.rs.demo.exception.NotFoundException;
import raf.rs.demo.mapper.UserMapper;
import raf.rs.demo.model.User;
import raf.rs.demo.repositories.UserRepository;
import raf.rs.demo.security.TokenService;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImp implements UserService{

    private UserRepository userRepository;
    private UserMapper userMapper;

    private RestTemplate emailServiceRestTemplate;

    private TokenService tokenService;

    public UserServiceImp(UserRepository userRepository, UserMapper userMapper, TokenService tokenService, RestTemplate emailServiceRestTemplate){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.emailServiceRestTemplate = emailServiceRestTemplate;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto addClient(CreateUserClient clientDto) {
        User user = userMapper.userCreateClientToUser(clientDto);
        userRepository.save(user);
        UserMailDto umd = new UserMailDto();
        umd.setName(clientDto.getName());
        umd.setSurname(clientDto.getSurname());
        umd.setEmail(clientDto.getEmail());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<UserMailDto> request = new HttpEntity<>(umd, headers);

        emailServiceRestTemplate.postForObject("/email/register", request, String.class);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto addManager(CreateUserManager managerDto) {
        User user = userMapper.userCreateManagerToUser(managerDto);
        userRepository.save(user);
        UserMailDto umd = new UserMailDto();
        umd.setName(managerDto.getName());
        umd.setSurname(managerDto.getSurname());
        umd.setEmail(managerDto.getEmail());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<UserMailDto> request = new HttpEntity<>(umd, headers);

        emailServiceRestTemplate.postForObject("/email/register", request, String.class);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateClient(Long id, CreateUserClient clientDto) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));

        u.setUsername(clientDto.getUsername());
        u.setPassword(clientDto.getPassword());
        u.setEmail(clientDto.getEmail());
        u.setPhoneNumber(clientDto.getPhoneNumber());
        u.setName(clientDto.getName());
        u.setSurname(clientDto.getSurname());
        u.setDateOfBirth(clientDto.getDateOfBirth());
        u.setPassportNumber(clientDto.getPassportNumber());
        return userMapper.userToUserDto(userRepository.save(u));
    }

    @Override
    public UserDto updateManager(Long id, CreateUserManager managerDto) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        u.setUsername(managerDto.getUsername());
        u.setPassword(managerDto.getPassword());
        u.setEmail(managerDto.getEmail());
        u.setPhoneNumber(managerDto.getPhoneNumber());
        u.setName(managerDto.getName());
        u.setSurname(managerDto.getSurname());
        u.setDateOfBirth(managerDto.getDateOfBirth());
        u.setCompanyName(managerDto.getCompanyName());
        u.setDateOfWork(managerDto.getDateOfWork());
        return userMapper.userToUserDto(userRepository.save(u));
    }

    @Override
    public UserDto sendMailForUser(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s not found.", username)));

        UserMailDto umd = new UserMailDto();
        umd.setName(user.getName());
        umd.setSurname(user.getSurname());
        umd.setEmail(user.getEmail());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<UserMailDto> request = new HttpEntity<>(umd, headers);

        emailServiceRestTemplate.postForObject("/email/password", request, String.class);

        return userMapper.userToUserDto(user);
    }

    @Override
    public void changePassword(Long id, String password) {
        userRepository.changePassword(password, id);
    }

    @Override
    public void setActive(Long id) {
        userRepository.activateUser(id);
    }

    @Override
    public void setInactive(Long id) {
        userRepository.deactiveteUser(id);
    }


    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        System.out.println("OVDE");
        //Try to find active user for specified credentials
        User user = userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
}
