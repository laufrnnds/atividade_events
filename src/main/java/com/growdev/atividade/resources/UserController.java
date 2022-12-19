package com.growdev.atividade.resources;

import com.growdev.atividade.dto.UserDTO;
import com.growdev.atividade.dto.UserInsertDTO;
import com.growdev.atividade.dto.UserUpdateDTO;
import com.growdev.atividade.repositories.RoleRepository;
import com.growdev.atividade.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllUser(Pageable pageable) {
        Page<UserDTO> list = service.findAllPagedUser(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findByIdUser(@PathVariable Long id) {
        UserDTO dto = service.findByIdUser(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insertUser(@Valid @RequestBody UserInsertDTO dto){
        UserDTO newDto = service.saveUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newDto.getId())
                .toUri();
        return ResponseEntity.ok().body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserUpdateDTO dto, @PathVariable Long id){
        UserDTO newDto = service.updateUser(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
       String msg = service.deleteUser(id);
        return ResponseEntity.ok().body(msg);
    }
}
