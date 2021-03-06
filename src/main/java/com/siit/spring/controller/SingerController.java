package com.siit.spring.controller;

import com.siit.spring.domain.model.Singer;
import com.siit.spring.exception.SingerNotFoundException;
import com.siit.spring.service.SingerService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/singers")
@AllArgsConstructor
public class SingerController {

    private final SingerService service;

    @GetMapping("/{id}") //    /singers/10
    @ResponseStatus(HttpStatus.OK)
    public Singer getOneSinger(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @GetMapping //    /singers
    @ResponseStatus(HttpStatus.OK)
    public List<Singer> getAllSingers() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Singer create(@RequestBody Singer singer) {
        return service.create(singer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") long id, @RequestBody Singer singer) {
        singer.setId(id);
        service.updateTransactional(singer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @ExceptionHandler(SingerNotFoundException.class)
    public void notFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}
