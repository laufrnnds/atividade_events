package com.growdev.atividade.services;

import com.growdev.atividade.dto.CityDTO;
import com.growdev.atividade.entities.City;
import com.growdev.atividade.exceptions.DatabaseException;
import com.growdev.atividade.exceptions.ResourceNotFoundException;
import com.growdev.atividade.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CityService {

    @Autowired
    private CityRepository repository;

    @Transactional(readOnly = true)
    public Page<CityDTO> findAllPagedCity (Pageable pageable){
        Page<City> lista = repository.findAll(pageable);
        return lista.map(city -> new CityDTO(city));
    }

    @Transactional(readOnly = true)
    public CityDTO findByIdCity(Long id) {
            Optional<City> cidade = repository.findById(id);
            City entity = cidade.orElseThrow(() -> new ResourceNotFoundException("Not found " + id));
            return new CityDTO(entity);
    }

    public CityDTO saveCity (CityDTO dto){
        City entity = new City();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CityDTO(entity);
    }

    public CityDTO updateCity (Long id, CityDTO dto){

        try {
            City entity = repository.findById(id).get();
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CityDTO(entity);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Intregrity violation");
        }
    }

    public String deleteCity (Long id){

        try {
            repository.deleteById(id);
            return "City Apagada";
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Intregrity violation");
        }
    }

}
