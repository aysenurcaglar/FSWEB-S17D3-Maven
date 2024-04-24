package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public ResponseEntity<List<Koala>> getAllKoalas() {
        List<Koala> list = new ArrayList<>(koalas.values());
        if (koalas.isEmpty()) {
            throw new ZooException("No koalas found.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Koala> getKoalaById(@PathVariable int id) {
        Koala koala = koalas.get(id);
        if (koala == null) {
            throw new ZooException("Koala with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(koala, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Koala> addKoala(@RequestBody Koala newKoala) {
        if (newKoala.getId() == null) {
            throw new IllegalArgumentException();
        }
        if (koalas.containsKey(newKoala.getId())) {
            throw new ZooException("Koala with ID " + newKoala.getId() + " already exists.", HttpStatus.CONFLICT);
        }
        koalas.put(newKoala.getId(), newKoala);
        return new ResponseEntity<>(newKoala, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Koala> updateKoala(@PathVariable int id, @RequestBody Koala updatedKoala) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        koalas.put(id, updatedKoala);
        return new ResponseEntity<>(updatedKoala, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Koala> deleteKoala(@PathVariable int id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        Koala removedKoala = koalas.remove(id);
        return new ResponseEntity<>(removedKoala, HttpStatus.OK);
    }
}
