package ru.practicum.ewm.service.event;

import org.junit.jupiter.api.Test;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.enums.RequestStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceImplTest {
    private EventServiceImpl eventService;

    @Test
    void changeStatusRequestsTest() {
        Long userId = 1L;
        Long eventId = 1L;
        Long userId2 = 2L;
        Long eventId2 = 2L;
        EventRequestStatusUpdateRequest requestToStatusConfirmed = new EventRequestStatusUpdateRequest();
        requestToStatusConfirmed.setRequestIds(List.of(1L, 2L));
        requestToStatusConfirmed.setStatus(RequestStatus.CONFIRMED);

        EventRequestStatusUpdateRequest requestToStatusRejected = new EventRequestStatusUpdateRequest();
        requestToStatusConfirmed.setRequestIds(List.of(3L, 4L));
        requestToStatusConfirmed.setStatus(RequestStatus.REJECTED);

        EventRequestStatusUpdateResult eventRequestStatusUpdateResultConf = eventService.changeStatusRequests(userId, eventId, requestToStatusConfirmed);
        EventRequestStatusUpdateResult eventRequestStatusUpdateResultReject = eventService.changeStatusRequests(userId2, eventId2, requestToStatusRejected);

        System.out.println();
    }
}
/*
public EventRequestStatusUpdateResult changeStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest requestToStatusUpdate) {
        log.info("Private: Изменение статуса заявок на участие в событии с id {} пользователя с id {}", userId, eventId);
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь %s не найден", userId)));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Событие %s не найдено", eventId)));
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            log.info("Подтверждение заявок на данное событие не требуется");
            throw new NotAvailableException("Подтверждение заявок на данное событие не требуется");
        }

        Long confirmedRequests = requestRepository.countAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);

        long freePlaces = event.getParticipantLimit() - confirmedRequests;

        RequestStatus status = RequestStatus.valueOf(String.valueOf(requestToStatusUpdate.getStatus()));

        if (status.equals(RequestStatus.CONFIRMED) && freePlaces <= 0) {
            throw new NotAvailableException("Заявки на участие в данном событии больше не принимаются");
        }

        List<Request> requests = requestRepository.findAllByEventIdAndEventInitiatorIdAndIdIn(eventId,
                userId, requestToStatusUpdate.getRequestIds());
        List<ParticipationRequestDto> confirmedRequestsDto = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequestsDto = new ArrayList<>();

        // setStatus(requests, status, freePlaces);

       /* List<ParticipationRequestDto> requestsDto = requests
                .stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());*/

//        for(Request request:requests){
//                if(freePlaces>0){
//                confirmedRequestsDto.add(mapper.map(request,ParticipationRequestDto.class));
//        freePlaces--;
//        }else{
//        rejectedRequestsDto.add(mapper.map(request,ParticipationRequestDto.class));
//        }
//        }

       /* requestsDto.forEach(el -> {
            if (status.equals(RequestStatus.CONFIRMED)) {
                confirmedRequestsDto.add(el);
            } else {
                rejectedRequestsDto.add(el);
            }
        });*/

       // return new EventRequestStatusUpdateResult(confirmedRequestsDto,rejectedRequestsDto);
