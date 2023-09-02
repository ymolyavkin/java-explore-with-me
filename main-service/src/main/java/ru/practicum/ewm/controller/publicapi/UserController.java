package ru.practicum.ewm.controller.publicapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.user.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin/users")
@Slf4j
@Validated
@RequiredArgsConstructor
public class UserController {
    @GetMapping
    public ResponseEntity<Object> test(HttpServletRequest request) {
        log.info("Получен тестовый запрос на эндпоинт /users GET");

        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());

        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody UserDto userDto) {
        log.info("Получен тестовый запрос на эндпоинт /users POST");

        return null;
    }
}
