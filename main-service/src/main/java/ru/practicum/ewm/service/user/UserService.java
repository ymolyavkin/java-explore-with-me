package ru.practicum.ewm.service.user;

import ru.practicum.ewm.dto.user.NewUserRequest;
import ru.practicum.ewm.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(NewUserRequest newUserRequest);
    List<UserDto> getUsers(List<Long> userIds, int from, int size);
    void deleteUserById(Long id);
}
