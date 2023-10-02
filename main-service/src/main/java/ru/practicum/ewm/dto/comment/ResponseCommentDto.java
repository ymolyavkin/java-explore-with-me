package ru.practicum.ewm.dto.comment;
import lombok.*;
import ru.practicum.ewm.dto.user.UserShortDto;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseCommentDto {
    private Long id;
    private String text;
    private LocalDateTime created;
    private UserShortDto author;
    private Long eventId;
}
