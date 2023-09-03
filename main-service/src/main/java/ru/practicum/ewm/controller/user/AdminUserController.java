package ru.practicum.ewm.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.user.NewUserRequest;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/admin/users")
@Slf4j
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;
    /*@GetMapping
    public ResponseEntity<Object> test(HttpServletRequest request) {
        log.info("Получен тестовый запрос на эндпоинт /users GET");

        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());

        return null;
    }
*/
    @GetMapping
    public ResponseEntity<Object> getUserInfo() {
        log.info("Получен запрос на получение информации о пользователях");

        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> addNewUser(@Valid @RequestBody
                                             NewUserRequest newUserRequest) {
        log.info("Получен запрос на добавление нового пользователя");
        return new ResponseEntity<>(userService.addUser(newUserRequest), HttpStatus.OK);
       // return userService.addUser(newUserRequest);
    }
}
