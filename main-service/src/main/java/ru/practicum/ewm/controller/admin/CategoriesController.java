package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.validator.Marker;

@Controller
@RequestMapping(value = "/admin/categories")
@Slf4j
@Validated
@RequiredArgsConstructor
public class CategoriesController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addNewCategory(@Validated(Marker.OnCreate.class)
                                                 @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Получен запрос на добавление новой категории");

        return null;
    }

    @PatchMapping
    public ResponseEntity<Object> updateCategory(@Validated(Marker.OnUpdate.class)
                                                 @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Получен запрос на обновление категории");

        return null;
    }
}
