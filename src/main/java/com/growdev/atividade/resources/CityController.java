package com.growdev.atividade.resources;

import com.growdev.atividade.dto.CityDTO;
import com.growdev.atividade.dto.EventDTO;
import com.growdev.atividade.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    @Autowired
    private CityService service;

    // Pageable já me retorna um padrão ( retorna sem a url )
    // caso eu queira retornar a minha pagina manipulada eu passo a url
    @GetMapping("/all") // url = ?page=0&size=12&sort=name,asc
    public ResponseEntity<Page<CityDTO>> findAllCity (Pageable pageable){
        Page<CityDTO> lista = service.findAllPagedCity(pageable);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<CityDTO> findByIdCity(@PathVariable Long id){
        CityDTO dto = service.findByIdCity(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/salvar")
    public ResponseEntity<CityDTO> insertCity (@RequestBody CityDTO dto){
        dto = service.saveCity(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(@RequestBody CityDTO dto, @PathVariable Long id){
        CityDTO newDto = service.updateCity(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id){
        String msg = service.deleteCity(id);
        return ResponseEntity.ok().body(msg);
    }
}
