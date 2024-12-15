package org.prd.bookservice.model.repository;

import org.prd.bookservice.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByCode(String code);
    boolean existsByCode(String code);
}