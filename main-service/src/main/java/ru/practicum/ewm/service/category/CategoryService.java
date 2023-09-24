package ru.practicum.ewm.service.category;

import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto editCategory(Long id, NewCategoryDto newCategoryDto);

    Boolean deleteCategoryById(long id);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(long id);
}
