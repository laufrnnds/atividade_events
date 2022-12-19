package com.growdev.atividade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO extends UserDTO  {
    private String senha;
}
