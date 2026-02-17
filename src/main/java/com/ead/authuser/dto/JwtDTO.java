package com.ead.authuser.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtDTO {

    @NotNull
    private String token;
    private String type = "Bearer";

    public JwtDTO(){
    }

    public JwtDTO(String token){
        this.token = token;
    }
}
