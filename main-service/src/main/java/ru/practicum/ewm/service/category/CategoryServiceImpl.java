package ru.practicum.ewm.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.exception.AlreadyExistsException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final ModelMapper mapper;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        log.info("Добавление новой категории");
        Category category = categoryRepository.save(mapper.map(newCategoryDto, Category.class));

        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto editCategory(Long id, NewCategoryDto newCategoryDto) {
        Category updated = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Категория %s не найдена", id)));

        if (newCategoryDto.getName() != null && !newCategoryDto.getName().isBlank()) {
            updated.setName(newCategoryDto.getName());
        }

        return mapper.map(categoryRepository.save(updated), CategoryDto.class);
    }

    @Override
    public Boolean deleteCategoryById(long id) {
        log.info("Удаление категории с id = {}", id);
        if (!CollectionUtils.isEmpty(eventRepository.findAllByCategoryId(id))) {
            throw new AlreadyExistsException
                    ("С категорией существуют связанные события");
        }
        boolean isFound = categoryRepository.existsById(id);
        if (isFound) {
            categoryRepository.deleteById(id);
        }
        return isFound;
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        log.info("Получение информации о категориях");

        Page<Category> categoriesPage = categoryRepository.findAll(PageRequest.of(from, size));
        List<Category> categories = categoriesPage.getContent();

        return categories.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(long id) {
        log.info("Получение информации о категории с id = {}", id);

        return mapper.map(categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Категория %s не найдена", id))), CategoryDto.class);
    }
}
