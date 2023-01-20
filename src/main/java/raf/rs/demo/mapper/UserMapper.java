package raf.rs.demo.mapper;

import org.springframework.stereotype.Component;
import raf.rs.demo.dto.CreateUserClient;
import raf.rs.demo.dto.CreateUserManager;
import raf.rs.demo.dto.UserDto;
import raf.rs.demo.model.User;
import raf.rs.demo.repositories.RoleRepository;

@Component
public class UserMapper {

    private RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setPassportNumber(user.getPassportNumber());
        userDto.setDaysOfRenting(user.getDaysOfRenting());
        userDto.setCompanyName(user.getCompanyName());
        userDto.setDateOfWork(user.getDateOfWork());
        userDto.setActive(user.isActive());
        userDto.setRank(user.getRank());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public User userCreateClientToUser(CreateUserClient createUserClient){
        User user = new User();
        user.setUsername(createUserClient.getUsername());
        user.setEmail(createUserClient.getEmail());
        user.setPassword(createUserClient.getPassword());
        user.setPhoneNumber(createUserClient.getPhoneNumber());
        user.setName(createUserClient.getName());
        user.setSurname(createUserClient.getSurname());
        user.setDateOfBirth(createUserClient.getDateOfBirth());
        user.setPassportNumber(createUserClient.getPassportNumber());
        user.setDaysOfRenting(0);
        user.setActive(false);
        user.setRank(null);
        user.setRole(this.roleRepository.findRoleByName(createUserClient.getRole()));
        return user;
    }

    public User userCreateManagerToUser(CreateUserManager createUserManager){
        User user = new User();
        user.setUsername(createUserManager.getUsername());
        user.setEmail(createUserManager.getEmail());
        user.setPassword(createUserManager.getPassword());
        user.setPhoneNumber(createUserManager.getPhoneNumber());
        user.setName(createUserManager.getName());
        user.setSurname(createUserManager.getSurname());
        user.setDateOfBirth(createUserManager.getDateOfBirth());
        user.setCompanyName(createUserManager.getCompanyName());
        user.setDateOfWork(createUserManager.getDateOfWork());
        user.setActive(false);
        user.setRank(null);
        user.setRole(this.roleRepository.findRoleByName(createUserManager.getRole()));
        return user;
    }

}
