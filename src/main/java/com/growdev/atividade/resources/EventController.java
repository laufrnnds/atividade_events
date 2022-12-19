package com.growdev.atividade.resources;

import com.growdev.atividade.dto.EventDTO;
import com.growdev.atividade.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private EventService service;

    // PageRequest já me retorna um padrão ( retorna sem a url )
    // caso eu queira retornar a minha pagina manipulada e  url com os values dos params personalizados
    @GetMapping("/all") // url = ?pagina=0&linhasPorPagina=10&direcao=asc&ordenado=name
    public ResponseEntity<Page<EventDTO>> findAllEvent (
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "10") Integer linhasPorPagina,
            @RequestParam(value = "direcao", defaultValue = "ASC") String direcao,
            @RequestParam(value = "ordenado", defaultValue = "name") String nome
    ){
        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), nome);
        Page<EventDTO> lista = service.findAllPagedEvent(pageRequest);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<EventDTO> findByIdEvent (@PathVariable Long id){
        EventDTO dto = service.findByIdEvent(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/salvar")
    public ResponseEntity<EventDTO> insertEvent (@Valid @RequestBody EventDTO dtom){
        EventDTO dto = service.salvarEvent(dtom);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@RequestBody EventDTO dto, @PathVariable Long id){
        EventDTO newDto = service.updateEvent(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id){
        String msg = service.deleteEvent(id);
        return ResponseEntity.ok().body(msg);
    }

}
