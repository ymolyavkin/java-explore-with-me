package ru.practicum.ewm.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.ewm.service.category.CategoryService;
import ru.practicum.validator.Marker;

@Controller
@RequestMapping(value = "/admin/categories")
@Slf4j
@RequiredArgsConstructor
public class AdminCategoriesController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> addNewCategory(@Validated(Marker.OnCreate.class)
                                                 @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Получен запрос на добавление новой категории");

        return new ResponseEntity<>(categoryService.addCategory(newCategoryDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Validated(Marker.OnUpdate.class)
                                                      @RequestBody NewCategoryDto newCategoryDto,
                                                      @PathVariable Long id) {
        log.info("Получен запрос на обновление категориис id {}", id);

        return new ResponseEntity<>(categoryService.editCategory(id, newCategoryDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        log.info("Получен запрос на удаление категории с id {}", id);

        if (categoryService.deleteCategoryById(id)) {
            return new ResponseEntity<>("Категория удалена", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Категория не найдена или недоступна", HttpStatus.NOT_FOUND);
        }
    }
}
