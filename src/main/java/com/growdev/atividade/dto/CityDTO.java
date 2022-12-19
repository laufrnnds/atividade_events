package com.growdev.atividade.dto;

import com.growdev.atividade.entities.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityDTO {

    private Long id;

    @NotBlank(message = "Campo Obrigatório")
    private String name;

    public CityDTO(City entity){
        id = entity.getId();
        name = entity.getName();
    }
}
