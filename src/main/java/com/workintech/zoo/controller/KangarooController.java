package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private Map<Integer, Kangaroo> kangaroos;

    public KangarooController() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public ResponseEntity<List<Kangaroo>> getAllKangaroos() {

        List<Kangaroo> list = new ArrayList<>(kangaroos.values());

        if (list.isEmpty()) {
            throw new ZooException("No kangaroos found.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kangaroo> getKangarooById(@PathVariable int id) {
        Kangaroo kangaroo = kangaroos.get(id);
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(kangaroo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Kangaroo> addKangaroo(@RequestBody Kangaroo newKangaroo) {
        if (newKangaroo.getId() <= 0 || newKangaroo.getName() == null || newKangaroo.getName().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (kangaroos.containsKey(newKangaroo.getId())) {
            throw new ZooException("Kangaroo with ID " + newKangaroo.getId() + " already exists.", HttpStatus.CONFLICT);
        }
        kangaroos.put(newKangaroo.getId(), newKangaroo);
        return new ResponseEntity<>(newKangaroo, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kangaroo> updateKangaroo(@PathVariable int id, @RequestBody Kangaroo updatedKangaroo) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        kangaroos.put(id, updatedKangaroo);
        return new ResponseEntity<>(updatedKangaroo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kangaroo> deleteKangaroo(@PathVariable int id) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        Kangaroo removedKangaroo = kangaroos.remove(id);
        return new ResponseEntity<>(removedKangaroo, HttpStatus.OK);
    }
}
