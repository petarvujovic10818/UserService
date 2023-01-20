package raf.rs.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class TokenRequestDto {

    private String username;

    private String password;

    public TokenRequestDto() {
    }

    public TokenRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
