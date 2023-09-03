package ru.practicum.ewm.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.user.NewUserRequest;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    //@Autowired
    private final ModelMapper mapper;

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        User user = userRepository.save(mapper.map(newUserRequest, User.class));

        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        // return UserMapper.mapToUserDto(users);
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id).ifPresent(user -> userRepository.deleteById(id));
    }
}
