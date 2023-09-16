package ru.practicum.ewm.service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.entity.Compilation;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.repository.EventRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final ModelMapper mapper;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        log.info("Public: Получение информации о подборках");
        PageRequest page = PageRequest.of(from / size, size);
        if (pinned != null) {
            String pinnedStr = pinned ? "true" : "false";
            return compilationRepository.findAllByPinned(pinnedStr, page)
                    .stream()
                    .map(compilation -> mapper.map(compilation, CompilationDto.class))
                    .collect(Collectors.toList());
        }
        Page<Compilation> compilationsPage = compilationRepository.findAll(page);
        List<Compilation> compilations = compilationsPage.getContent();

        return compilations.stream().map(compilation -> mapper.map(compilation, CompilationDto.class)).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(long id) {
        log.info("Получение информации о категории с id = {}", id);

        return mapper.map(compilationRepository.findById(id), CompilationDto.class);
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        log.info("Добавление новой подборки");
        Compilation compilation;
        if (newCompilationDto.getEvents()!=null){
            Set<Event> events = eventRepository.findByIdIn(newCompilationDto.getEvents());
            compilation = mapper.map(newCompilationDto, Compilation.class);
            compilation.setEvents(events);
        } else {
            compilation = mapper.map(newCompilationDto, Compilation.class);
        }
      //  Set<Event> events = eventRepository.findByIdIn(newCompilationDto.getEvents());
     //  Compilation compilation = mapper.map(newCompilationDto, Compilation.class);
      //  compilation.setEvents(events);
        compilationRepository.save(compilation);

        return mapper.map(compilation, CompilationDto.class);
    }

    @Override
    public CompilationDto updateCompilation(Long id, UpdateCompilationRequest updateCompRequest) {
        log.info("Admin: Обновление информации о подборке с id {}", id);
        Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Подборка %s не найдена", id)));

        if (updateCompRequest.getTitle() != null && !updateCompRequest.getTitle().isBlank()) {
            compilation.setTitle(updateCompRequest.getTitle());
        }
        if (updateCompRequest.getPinned() != null) {
            compilation.setPinned(updateCompRequest.getPinned());
        }

        if (updateCompRequest.getEvents() != null && !updateCompRequest.getEvents().isEmpty()) {
            List<Long> eventIds = updateCompRequest.getEvents();
            List<Event> events = eventRepository.findAllByIdIn(eventIds);
            compilation.setEvents(new HashSet<>(events));
        }
        return mapper.map(compilationRepository.save(compilation), CompilationDto.class);
    }

    @Override
    public Boolean deleteCompilationById(long id) {
        log.info("Удаление подборки с id {}", id);
        boolean isFound = compilationRepository.existsById(id);
        if (isFound) {
            compilationRepository.deleteById(id);
        }
        return isFound;
    }
}