package com.example.filmservice.service.impl;

import com.example.filmservice.dto.FilmDTO;
import com.example.filmservice.entity.Film;
import com.example.filmservice.entity.Schedule;
import com.example.filmservice.repository.FilmRepository;
import com.example.filmservice.repository.ScheduleRepository;
import com.example.filmservice.service.FilmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {

    @Autowired
    FilmRepository filmRepository;
    ScheduleRepository scheduleRepository;
    @Override
    public Film insertFilm(Film film) {
        Film result = filmRepository.save(film);
        return result;
    }

    @Override
    public List<Film> getAllFilm() {
        return filmRepository.findAll();
    }

    @Override
    public Film updateFilm(Integer film_code, Film film) {
        Film result = findById(film_code);
        if (result != null) {
            result.setFilm_name(film.getFilm_name());
            result.setSedangTayang(film.getSedangTayang());
            result.setPrice(film.getPrice());
            return filmRepository.save(result);
        }
        return null;
    }

    @Override
    public Boolean delete(Integer film_code) {
        Film result = findById(film_code);
        if (result != null) {
            filmRepository.deleteById(film_code);
            return true;
        }
        return false;
    }

    @Override
    public Schedule getSchedule(Integer film_code) {
        Optional<Schedule> result = scheduleRepository.findById(film_code);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    @Override
    public Film findById(Integer film_code) {
        Optional<Film> result = filmRepository.findById(film_code);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    @Override
    public List<Film> findOnShowingFilm(Boolean sedangTayang) {
        List<Film> result = filmRepository.findBySedangTayang(sedangTayang);
        return result;
    }

    ObjectMapper mapper = new ObjectMapper();
    @Override
    public FilmDTO mapToDto(Film film) {
        return mapper.convertValue(film, FilmDTO.class);
    }

    @Override
    public Film mapToEntity(FilmDTO filmDTO) {
        return mapper.convertValue(filmDTO, Film.class);
    }
}
