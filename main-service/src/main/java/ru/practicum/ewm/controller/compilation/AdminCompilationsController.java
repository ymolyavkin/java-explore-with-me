package ru.practicum.ewm.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.validator.Marker;

@RestController
@RequestMapping(value = "/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class AdminCompilationsController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompilationDto> addNewCompilation(@Validated(Marker.OnCreate.class)
                                                            @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Получен запрос на добавление новой подборки");

        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompilationDto> updateCompilation(@Validated(Marker.OnUpdate.class)
                                                            @RequestBody NewCompilationDto newCompilationDto,
                                                            @PathVariable Long id) {
        log.info("Получен запрос на обновление информации о подборке с id {}", id);

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompilationById(@PathVariable Long id) {
        log.info("Получен запрос на удаление подборки с id {}", id);

        return null;
    }
}
