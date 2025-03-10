package com.vinay.jenkins.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinay.jenkins.model.Entity;
import com.vinay.jenkins.repository.EntityRepo;

@RestController
@RequestMapping("/api/entities")
public class EntityController {

    @Autowired
    private EntityRepo entityRepository;

    @PostMapping("/add")
    public ResponseEntity<Entity> addEntity(@RequestBody Entity entity) {
        return ResponseEntity.ok(entityRepository.save(entity));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Entity>> getAllEntities() {
        return ResponseEntity.ok(entityRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntityById(@PathVariable String id) {
        Optional<Entity> entity = entityRepository.findById(id);
        return entity.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable String id, @RequestBody Entity updatedEntity) {
        return entityRepository.findById(id).map(entity -> {
            entity.setName(updatedEntity.getName());
            entity.setType(updatedEntity.getType());
            return ResponseEntity.ok(entityRepository.save(entity));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable String id) {
        if (entityRepository.existsById(id)) {
            entityRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok("jenkins  trigger");
    }
    
}

