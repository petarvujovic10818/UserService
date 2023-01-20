package raf.rs.demo.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rs.demo.dto.*;
import raf.rs.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
//    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader("Authorization") String authorization, Pageable pageable) {

        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/manager")
    public ResponseEntity<UserDto> saveManager(@RequestBody @Valid CreateUserManager userCreateManager) {
        return new ResponseEntity<>(userService.addManager(userCreateManager), HttpStatus.CREATED);
    }

    @PostMapping("/client")
    public ResponseEntity<UserDto> saveClient(@RequestBody @Valid CreateUserClient userCreateClient) {
        return new ResponseEntity<>(userService.addClient(userCreateClient), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassowrd(@RequestBody ForgotPassordDto forgotPassordDto){
        return new ResponseEntity<>(userService.sendMailForUser(forgotPassordDto.getUsername()), HttpStatus.OK);
    }

    @PatchMapping("/forgotPassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDto changePasswordDto){
        userService.changePassword(id, changePasswordDto.getPassword());
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateUser(@PathVariable Long id){
        userService.setActive(id);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id){
        userService.setInactive(id);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<UserDto> updateClient(@PathVariable("id") Long id, @RequestBody @Valid CreateUserClient clientDto){
        return new ResponseEntity<>(userService.updateClient(id, clientDto), HttpStatus.OK);
    }

    @PutMapping("/manager/{id}")
    public ResponseEntity<UserDto> updateManager(@PathVariable("id") Long id, @RequestBody @Valid CreateUserManager managerDto){
        return new ResponseEntity<>(userService.updateManager(id, managerDto), HttpStatus.OK);
    }

}
