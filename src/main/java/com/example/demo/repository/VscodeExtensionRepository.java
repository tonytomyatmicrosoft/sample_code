package com.example.demo.repository;

import com.example.demo.model.VscodeExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VscodeExtensionRepository extends JpaRepository<VscodeExtension, Long> {

    List<VscodeExtension> findByPublisher(String publisher);

    List<VscodeExtension> findByNameContainingIgnoreCase(String name);

    List<VscodeExtension> findByRatingGreaterThanEqual(double rating);
}
