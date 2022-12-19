package com.growdev.atividade.services;

import com.growdev.atividade.dto.RoleDTO;
import com.growdev.atividade.dto.UserDTO;
import com.growdev.atividade.dto.UserInsertDTO;
import com.growdev.atividade.dto.UserUpdateDTO;
import com.growdev.atividade.entities.Role;
import com.growdev.atividade.entities.User;
import com.growdev.atividade.exceptions.DatabaseException;
import com.growdev.atividade.exceptions.ResourceNotFoundException;
import com.growdev.atividade.repositories.RoleRepository;
import com.growdev.atividade.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPagedUser(Pageable pageable) {
        Page<User> usuarios = repository.findAll(pageable);
        return usuarios.map(usuario -> new UserDTO(usuario));
      }

    @Transactional(readOnly = true)
    public UserDTO findByIdUser(Long id) {

            Optional<User> usuario = repository.findById(id);
            User entity = usuario.orElseThrow(() -> new ResourceNotFoundException("Not found " + id));
            return new UserDTO(entity);
    }

    public UserDTO saveUser(UserInsertDTO dto) {
        User entity = new User();
        setEntityInsert(entity, dto);
        entity = repository.save(entity);
        return new UserDTO(entity);
    }

    public UserDTO updateUser(Long id, UserUpdateDTO dto) {
        try {
            User entity = repository.findById(id).get();
            setEntityUpdate(entity, dto);
            entity = repository.save(entity);

            return new UserDTO(entity);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Intregrity violation");
        }
    }

    public String deleteUser(Long id) {
        try {
            repository.deleteById(id);
            return "User Apagado";
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Intregrity violation");
        }
    }

    public void setEntityInsert(User entity, UserInsertDTO dto) {
        entity.setEmail(dto.getEmail());
        entity.setSenha(passwordEncoder.encode(dto.getSenha()));
        for (RoleDTO rolesDTO : dto.getRoles()) {
            Role roles = roleRepository.findById(rolesDTO.getId()).get();
            entity.getRoles().add(roles);
        }
    }

    public void setEntityUpdate(User entity, UserUpdateDTO dto) {
        entity.setEmail(dto.getEmail());
        entity.setSenha(passwordEncoder.encode(dto.getSenha()));
        for (RoleDTO rolesDTO : dto.getRoles()) {
            Role roles = roleRepository.findById(rolesDTO.getId()).get();
            entity.getRoles().add(roles);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usuario = repository.findByEmail(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Email não existe");
        }
        return usuario;
    }
}
