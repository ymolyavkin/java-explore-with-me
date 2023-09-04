package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.client.HitClient;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {
    private final HitClient hitClient;
}
