package ru.practicum.ewm.service.category;

import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.ewm.dto.user.UserDto;

import java.util.List;

public interface CategoryService {
    //admin: add, delete, patch
    //public: get
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto editCategory(NewCategoryDto newCategoryDto);

    void deleteCategoryById(long id);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(long id);
}
