package ru.practicum.ewm.controller.publicapi;

import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/categories")
@Slf4j
@RequiredArgsConstructor
public class PublicCategoriesController {
    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        log.info("Получен запрос на получение всех категорий");

        return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        log.info("Получен запрос на получение категории с id {}", id);

        return null;
    }
}
