package com.growdev.atividade.services;

import com.growdev.atividade.dto.EventDTO;
import com.growdev.atividade.entities.City;
import com.growdev.atividade.entities.Event;
import com.growdev.atividade.exceptions.DatabaseException;
import com.growdev.atividade.exceptions.ResourceNotFoundException;
import com.growdev.atividade.repositories.CityRepository;
import com.growdev.atividade.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAllPagedEvent (PageRequest pageRequest){
        Page<Event> lista = repository.findAll(pageRequest);
        return lista.map(event -> new EventDTO(event));
    }

    public EventDTO salvarEvent (EventDTO dto){
        Event entity = new Event();
        City city = cityRepository.findById(dto.getCityId()).get();
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        entity.setCity(city);
        entity = repository.save(entity);
        return new EventDTO(entity);
    }

    @Transactional(readOnly = true)
    public EventDTO findByIdEvent(Long id) {
            Optional<Event> evento = repository.findById(id);
            Event entity = evento.orElseThrow(() -> new ResourceNotFoundException("Not found " + id));
            return new EventDTO(entity);
    }

    public EventDTO updateEvent (Long id, EventDTO dto){

        try {
            Event entity = repository.findById(id).get();
            City city = cityRepository.findById(dto.getCityId()).get();
            entity.setName(dto.getName());
            entity.setDate(dto.getDate());
            entity.setUrl(dto.getUrl());
            entity.setCity(city);
            entity = repository.save(entity);
            return new EventDTO(entity);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Intregrity violation");
        }
    }

    public String deleteEvent (Long id){
        try {
            repository.deleteById(id);
            return "Event Apagado";
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Intregrity violation");
        }
    }
}
