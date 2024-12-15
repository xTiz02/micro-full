package org.prd.bookservice.model.repository;

import org.prd.bookservice.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByCode(String code);
    boolean existsByCode(String code);
    @Modifying
    @Query("UPDATE BookEntity b SET b.active = :state WHERE b.id = :id")
    void changeStatus(@Param("id") Long id, @Param("state") boolean status);

}