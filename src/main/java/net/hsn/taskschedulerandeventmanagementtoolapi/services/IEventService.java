package net.hsn.taskschedulerandeventmanagementtoolapi.services;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.EventDTO;

import java.time.LocalDate;
import java.util.List;

public interface IEventService {
    EventDTO addEvent(EventDTO eventDTO,Long userId);
    EventDTO editEvent(EventDTO eventDTO,Long userId);
    boolean deleteEvent(EventDTO eventDTO);
    EventDTO showEventById(Long id);
    List<EventDTO> showEventByUserId(Long userId);
    List<EventDTO> showEventsByUsername(String username);
    List<EventDTO> showAllEvents();
    EventDTO showEventByEventDate(LocalDate eventDate);
}
