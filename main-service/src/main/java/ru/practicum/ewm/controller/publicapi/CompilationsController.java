package ru.practicum.ewm.controller.publicapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationsController {
    @GetMapping
    public ResponseEntity<Object> test(HttpServletRequest request) {
        log.info("Получен тестовый запрос");
        System.out.println(request.getLocalAddr());
        System.out.println(request.getRemoteAddr());
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());

        return null;
    }

}
