package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.HitEntity;

public interface HitRepository extends JpaRepository<HitEntity, Long> {
}
