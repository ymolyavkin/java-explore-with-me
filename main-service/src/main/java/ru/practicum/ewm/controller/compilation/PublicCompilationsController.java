package ru.practicum.ewm.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.util.Constants.PAGE_DEFAULT_FROM;
import static ru.practicum.util.Constants.PAGE_DEFAULT_SIZE;

@RestController
@RequestMapping(value = "/compilations")
@Slf4j
@Validated
@RequiredArgsConstructor
public class PublicCompilationsController {
   /* @GetMapping
    public ResponseEntity<Object> test(HttpServletRequest request) {
        log.info("Получен тестовый запрос");
        System.out.println(request.getLocalAddr());
        System.out.println(request.getRemoteAddr());
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());

        return null;
    }*/

    @GetMapping
    public List<CompilationDto> getAllCompilations(@RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero int from,
                                                   // public ResponseEntity<Page<CompilationDto>> getAllCompilations(@Valid @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero int from,
                                                   @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive int size,
                                                   @RequestParam Boolean pinned) {
        log.info("Получен запрос на получение подборок событий");


        return null;
    }
    @GetMapping("/{id}")
    public CompilationDto getCompilationById(@PathVariable Long id) {
        log.info("Получен запрос на получение подборки событий с id {}", id);
        //return compilationService.getCompilationById(compId);
        return  null;
    }

}
