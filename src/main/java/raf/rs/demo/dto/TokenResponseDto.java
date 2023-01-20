package raf.rs.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class TokenResponseDto {

    private String token;

    public TokenResponseDto() {
    }
    public TokenResponseDto(String token) {
        this.token = token;
    }

}
