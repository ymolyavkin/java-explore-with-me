package ru.practicum.ewm.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.user.NewUserRequest;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        log.info("Добавление нового пользователя");
        User user = userRepository.save(mapper.map(newUserRequest, User.class));

        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getUsers(List<Long> userIds, int from, int size) {
        log.info("Получение информации о пользователях");
        List<User> users;
        Page<User> userPage;
        if (userIds != null) {
            users = userRepository.findAllByIdIn(userIds, PageRequest.of(from, size));
        } else {
            userPage = userRepository.findAll(PageRequest.of(from, size));
            users = userPage.getContent();
        }

        return users.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteUserById(long id) {
        log.info("Удаление пользователя с id = {}", id);
        boolean isFound = userRepository.existsById(id);
        if (isFound) {
            userRepository.deleteById(id);
        }
        return isFound;
    }
}
