package ru.practicum.ewm.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.service.compilation.CompilationService;
import ru.practicum.validator.Marker;

@Controller
@RequestMapping(value = "/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class AdminCompilationsController {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompilationDto> addNewCompilation(@Validated(Marker.OnCreate.class)
                                                            @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Получен запрос на добавление новой подборки");

        return new ResponseEntity<>(compilationService.addCompilation(newCompilationDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompilationDto> updateCompilation(@Validated(Marker.OnUpdate.class)
                                                            @RequestBody UpdateCompilationRequest updateCompilationRequest,
                                                            @PathVariable Long id) {
        log.info("Получен запрос на обновление информации о подборке с id {}", id);

        return ResponseEntity.ok(compilationService.updateCompilation(id, updateCompilationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompilationById(@PathVariable Long id) {
        log.info("Получен запрос на удаление подборки с id {}", id);

        if (compilationService.deleteCompilationById(id)) {
            return new ResponseEntity<>("Подборка удалена", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Подборка не найдена или недоступна", HttpStatus.NOT_FOUND);
        }
    }
}
