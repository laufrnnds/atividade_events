package com.growdev.atividade.dto;

import com.growdev.atividade.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDTO {
    private Long id;

    @NotBlank
    private String name;

    @Future(message = "TESTE FUTURO")
    private LocalDate date;

    private String url;

    @NotNull
    private Long cityId;

    public EventDTO(Event entity){
        id = entity.getId();
        name = entity.getName();
        date = entity.getDate();
        url = entity.getUrl();
        cityId = entity.getCity().getId();
    }


}
