package com.example.demo.controller;

import com.example.demo.model.VscodeExtension;
import com.example.demo.repository.VscodeExtensionRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vscode")
public class VscodeExtensionController {

    private final VscodeExtensionRepository repository;

    public VscodeExtensionController(VscodeExtensionRepository repository) {
        this.repository = repository;
    }

    // GET all extensions
    @GetMapping
    public List<VscodeExtension> getAll() {
        return repository.findAll();
    }

    // GET extension by id
    @GetMapping("/{id}")
    public ResponseEntity<VscodeExtension> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create a new extension
    @PostMapping
    public ResponseEntity<VscodeExtension> create(@Valid @RequestBody VscodeExtension extension) {
        VscodeExtension saved = repository.save(extension);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT update an extension
    @PutMapping("/{id}")
    public ResponseEntity<VscodeExtension> update(@PathVariable Long id, @Valid @RequestBody VscodeExtension details) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(details.getName());
                    existing.setDescription(details.getDescription());
                    existing.setPublisher(details.getPublisher());
                    existing.setDownloads(details.getDownloads());
                    existing.setRating(details.getRating());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE an extension
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(ext -> {
                    repository.delete(ext);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET by publisher
    @GetMapping("/publisher/{publisher}")
    public List<VscodeExtension> getByPublisher(@PathVariable String publisher) {
        return repository.findByPublisher(publisher);
    }

    // GET search by name
    @GetMapping("/search")
    public List<VscodeExtension> searchByName(@RequestParam String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    // GET by minimum rating
    @GetMapping("/rating/{minRating}")
    public List<VscodeExtension> getByMinRating(@PathVariable double minRating) {
        return repository.findByRatingGreaterThanEqual(minRating);
    }
}
