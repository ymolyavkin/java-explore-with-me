package ru.practicum.ewm.service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.entity.Compilation;
import ru.practicum.ewm.repository.CompilationRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final ModelMapper mapper;

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        log.info("Добавление новой подборки");
        Compilation compilation = compilationRepository.save(mapper.map(newCompilationDto, Compilation.class));

        return mapper.map(compilation, CompilationDto.class);
    }

    @Override
    public CompilationDto updateCompilation(Long id, NewCompilationDto newCompilationDto) {
        log.info("Обновление информации о подборке с id {}", id);
        return null;
    }

    @Override
    public Boolean deleteCompilationById(long id) {
        log.info("Удаление подборки с id {}", id);
        return null;
    }
}
/*
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
 */