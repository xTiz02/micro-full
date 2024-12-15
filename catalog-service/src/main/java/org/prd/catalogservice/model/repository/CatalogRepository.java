package org.prd.catalogservice.model.repository;

import org.prd.catalogservice.model.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogRepository extends MongoRepository<BookEntity, String> {

    Optional<BookEntity> findByCode(String code);
    Page<BookEntity> findByActive(boolean active, Pageable pageable);

    @Query("{ code : ?0}")
    @Update("{ $set: { active : ?1 } }")
    void updateActive(String code, boolean active);
    //Hacer que se tarigan las paginas de los libros activos

}