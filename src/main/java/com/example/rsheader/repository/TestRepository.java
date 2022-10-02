package com.example.rsheader.repository;

import com.example.rsheader.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("SELECT t FROM Test t WHERE (?1 IS NULL OR t.name LIKE %?1%)")
    Page<Test> findAll(String keyword, Pageable pageable);
}
