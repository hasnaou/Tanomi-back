package net.hsn.taskschedulerandeventmanagementtoolapi.services.serviceImpl;

import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Event;
import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.EventDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.EventRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    EventRepository eventRepository;
    @Mock
    UserRepository userRepository;
    ModelMapper modelMapper;
    @InjectMocks
    EventServiceImpl eventServiceImpl;

    @BeforeEach
    void setUp() {
        eventRepository = mock(EventRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = new ModelMapper();
        eventServiceImpl = new EventServiceImpl(eventRepository,modelMapper,userRepository);
    }

    @Test
    void addEvent() {
        User user = new User(1L);
        EventDTO eventDTO = EventDTO.builder()
                .id(1L)
                .title("Meeting")
                .description("Team meeting for project discussion")
                .eventDate(LocalDate.of(2024, 4, 2))
                .deleted(false)
                .user(user)
                .reminders(new ArrayList<>())
                .build();
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Meeting");
        event.setDescription("Team meeting for project discussion");
        event.setEventDate(LocalDate.of(2024, 4, 2));
        event.setDeleted(false);
        event.setUser(user);
        event.setReminders(new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        EventDTO result = eventServiceImpl.addEvent(eventDTO, 1L);

        assertEquals(eventDTO, result);
    }

    @Test
    void editEvent() {
        User user = new User(1L);
        EventDTO eventDTO = EventDTO.builder()
                .id(1L)
                .title("Meeting")
                .description("Team meeting for project discussion")
                .eventDate(LocalDate.of(2024, 4, 2))
                .deleted(false)
                .user(user)
                .reminders(new ArrayList<>())
                .build();

        Event savedEventEntity = new Event();
        savedEventEntity.setId(1L);
        savedEventEntity.setTitle("Meeting");
        savedEventEntity.setDescription("Team meeting for project discussion");
        savedEventEntity.setEventDate(LocalDate.of(2024, 4, 2));
        savedEventEntity.setDeleted(false);
        savedEventEntity.setUser(user);
        savedEventEntity.setReminders(new ArrayList<>());

        when(eventRepository.save(any(Event.class))).thenReturn(savedEventEntity);

        EventDTO result = eventServiceImpl.editEvent(eventDTO,1L);

        verify(eventRepository, times(1)).save(any(Event.class));

        assertNotNull(result);
        assertEquals(savedEventEntity.getId(), result.getId());
        assertEquals(savedEventEntity.getTitle(), result.getTitle());
    }

    @Test
    void deleteEvent() {
        User user = new User(1L);
        EventDTO eventDTO = EventDTO.builder()
                .id(1L)
                .title("Meeting")
                .description("Team meeting for project discussion")
                .eventDate(LocalDate.of(2024, 4, 2))
                .deleted(false)
                .user(user)
                .reminders(new ArrayList<>())
                .build();
        Event savedEventEntity = new Event();
        savedEventEntity.setId(1L);
        savedEventEntity.setTitle("Meeting");
        savedEventEntity.setDescription("Team meeting for project discussion");
        savedEventEntity.setEventDate(LocalDate.of(2024, 4, 2));
        savedEventEntity.setDeleted(true);
        savedEventEntity.setUser(user);
        savedEventEntity.setReminders(new ArrayList<>());

        when(eventRepository.save(any(Event.class))).thenReturn(savedEventEntity);

        boolean result = eventServiceImpl.deleteEvent(eventDTO);

        verify(eventRepository, times(1)).save(any(Event.class));
        assertTrue(result);
    }

    @Test
    void showEventById() {
        User user = new User(1L);
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Meeting");
        event.setDescription("Team meeting for project discussion");
        event.setEventDate(LocalDate.of(2024, 4, 2));
        event.setDeleted(false);
        event.setUser(user);
        event.setReminders(new ArrayList<>());

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        EventDTO result = eventServiceImpl.showEventById(1L);

        verify(eventRepository, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals(event.getId(), result.getId());
        assertEquals(event.getTitle(), result.getTitle());
    }

    @Test
    void showEventByUserId() {
        User user = new User(1L);
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Meeting");
        event.setDescription("Team meeting for project discussion");
        event.setEventDate(LocalDate.of(2024, 4, 2));
        event.setDeleted(false);
        event.setUser(user);
        event.setReminders(new ArrayList<>());

        when(eventRepository.findByUserId(1L)).thenReturn(Collections.singletonList(event));

        List<EventDTO> results = eventServiceImpl.showEventByUserId(1L);

        verify(eventRepository, times(1)).findByUserId(1L);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void showEventsByUsername() {
        User user = new User(1L);
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Meeting");
        event.setDescription("Team meeting for project discussion");
        event.setEventDate(LocalDate.of(2024, 4, 2));
        event.setDeleted(false);
        event.setUser(user);
        event.setReminders(new ArrayList<>());

        when(eventRepository.findEventByUsername("hasna")).thenReturn(Collections.singletonList(event));

        List<EventDTO> results = eventServiceImpl.showEventsByUsername("hasna");

        verify(eventRepository, times(1)).findEventByUsername("hasna");
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void showAllEvents() {
        List<Event> mockEvents = new ArrayList<>();
        User user = new User(1L,"hasna");
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Meeting");
        event.setDescription("Team meeting for project discussion");
        event.setEventDate(LocalDate.of(2024, 4, 2));
        event.setDeleted(false);
        event.setUser(user);
        event.setReminders(new ArrayList<>());
        mockEvents.add(event);
        mockEvents.add(event);

        when(eventRepository.findAll()).thenReturn(mockEvents);

        List<EventDTO> result = eventServiceImpl.showAllEvents();

        assertEquals(2, result.size());
    }

    @Test
    void showEventByEventDate() {
        User user = new User(1L);
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Meeting");
        event.setDescription("Team meeting for project discussion");
        event.setEventDate(LocalDate.of(2024, 4, 2));
        event.setDeleted(false);
        event.setUser(user);
        event.setReminders(new ArrayList<>());

        when(eventRepository.findEventByEventDate(LocalDate.of(2024,4,2))).thenReturn(event);

        EventDTO result = eventServiceImpl.showEventByEventDate(LocalDate.of(2024, 4, 2));

        verify(eventRepository, times(1)).findEventByEventDate(LocalDate.of(2024, 4, 2));
        assertNotNull(result);
        assertEquals(event.getId(), result.getId());
        assertEquals(event.getTitle(), result.getTitle());
    }
}