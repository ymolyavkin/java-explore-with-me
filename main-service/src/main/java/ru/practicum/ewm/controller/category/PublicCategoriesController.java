package ru.practicum.ewm.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.service.category.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.util.Constants.PAGE_DEFAULT_FROM;
import static ru.practicum.util.Constants.PAGE_DEFAULT_SIZE;

@Controller
@RequestMapping(value = "/categories")
@Slf4j
@Validated
@RequiredArgsConstructor
public class PublicCategoriesController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero int from,
                                                              @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive int size) {
        log.info("Получен запрос на получение всех категорий");

        return ResponseEntity.ok(categoryService.getCategories(from, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        log.info("Получен запрос на получение категории с id {}", id);

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
