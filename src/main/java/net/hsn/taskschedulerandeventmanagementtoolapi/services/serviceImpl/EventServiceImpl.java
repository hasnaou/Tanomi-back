package net.hsn.taskschedulerandeventmanagementtoolapi.services.serviceImpl;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.EventDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Event;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.EventRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.UserRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.services.IEventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, ModelMapper mapper, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public EventDTO addEvent(EventDTO eventDTO, Long userId) {
        //        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User currentUser = userRepository.findByUsername(userDetails.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()));
//        eventDTO.setUser(currentUser);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Event event = eventRepository.save(mapper.map(eventDTO, Event.class));
        event.setUser(user);
        event.setDeleted(Boolean.FALSE);
        event = eventRepository.save(event);
        return mapper.map(event, EventDTO.class);
    }

    @Override
    public EventDTO editEvent(EventDTO eventDTO, Long userId) {
        Event event = eventRepository.save(mapper.map(eventDTO, Event.class));
        return mapper.map(event, EventDTO.class);
    }

    @Override
    public boolean deleteEvent(EventDTO eventDTO) {
        eventDTO.setDeleted(Boolean.TRUE);
        Event event = eventRepository.save(mapper.map(eventDTO, Event.class));
        return event.isDeleted();
    }

    @Override
    public EventDTO showEventById(Long id) {
        Event event = eventRepository.findById(id).get();
        EventDTO eventDTO = mapper.map(event, EventDTO.class);
        return eventDTO;
    }

    @Override
    public List<EventDTO> showEventByUserId(@RequestHeader Long userId) {
        List<Event> events = eventRepository.findByUserId(userId);
        List<EventDTO> eventDTOList = events.stream().map(e -> mapper.map(e, EventDTO.class)).collect(Collectors.toList());
        return eventDTOList;
    }

    @Override
    public List<EventDTO> showEventsByUsername(String username) {
        List<Event> events = eventRepository.findEventByUsername(username);
        List<EventDTO> eventDTOS = events.stream().map(e -> mapper.map(e, EventDTO.class)).collect(Collectors.toList());
        return eventDTOS;
    }

    @Override
    public List<EventDTO> showAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOS = events.stream()
                .filter(event -> !event.isDeleted())
                .map(e -> mapper.map(e, EventDTO.class))
                .collect(Collectors.toList());
        return eventDTOS;
    }

    @Override
    public EventDTO showEventByEventDate(LocalDate eventDate) {
        EventDTO eventDTO = mapper.map(eventRepository.findEventByEventDate(eventDate), EventDTO.class);
        return eventDTO;
    }
}
