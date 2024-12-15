package org.prd.catalogservice.web.controller;

import org.prd.catalogservice.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/status/hellCheck")
    public String hellCheck(){
        return "Hello from Catalog Controller";
    }

    @GetMapping("/open/all")
    public ResponseEntity<?> findAllByPage(
            @RequestParam(defaultValue = "0", required = true) int page,
            @RequestParam(defaultValue = "5", required = true) int size,
            @RequestParam(defaultValue = "asc", required = true) String sort,
            @RequestParam(required = false) String field
    ){
        return  ResponseEntity.ok(catalogService.getBooks(page, size, sort, field));
    }

}