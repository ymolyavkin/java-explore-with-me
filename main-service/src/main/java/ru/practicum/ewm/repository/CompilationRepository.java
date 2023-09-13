package ru.practicum.ewm.repository;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.entity.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query("SELECT c FROM Compilation AS c WHERE CAST(c.pinned AS text) = :pinned")
    List<Compilation> findAllByPinned(String pinned, PageRequest page);
}
