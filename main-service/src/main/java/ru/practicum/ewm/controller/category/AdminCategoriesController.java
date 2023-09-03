package ru.practicum.ewm.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.validator.Marker;

@RestController
@RequestMapping(value = "/admin/categories")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminCategoriesController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addNewCategory(@Validated(Marker.OnCreate.class)
                                                 @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Получен запрос на добавление новой категории");

        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@Validated(Marker.OnUpdate.class)
                                                 @RequestBody NewCategoryDto newCategoryDto,
                                                 @PathVariable Long id) {
        log.info("Получен запрос на обновление категориис id {}", id);

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategoryById(@PathVariable Long id) {
        log.info("Получен запрос на удаление категории с id {}", id);

        return null;
    }
}
