package net.hsn.taskschedulerandeventmanagementtoolapi.controllers;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.EventDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.services.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/events")
public class EventController {
    private final IEventService iEventService;

    public EventController(IEventService iEventService) {
        this.iEventService = iEventService;
    }

    @PostMapping("/add")
    public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO eventDTO, @RequestHeader("User-ID") Long userId) {
        EventDTO eventDTO1 = iEventService.addEvent(eventDTO, userId);
        return new ResponseEntity<>(eventDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<EventDTO> editEvent(@RequestBody EventDTO eventDTO, @RequestHeader("User-ID") Long userId) {
        EventDTO eventDTO1 = iEventService.editEvent(eventDTO, userId);
        return new ResponseEntity<>(eventDTO1, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{eventId}")
    public Map<String, Boolean> deleteEvent(@PathVariable Long eventId) {
        EventDTO eventDTO = iEventService.showEventById(eventId);
        Map<String, Boolean> response = new HashMap<>();
        if(iEventService.deleteEvent(eventDTO)){
            response.put("deleted", Boolean.TRUE);
        }
        return response;
    }

    @GetMapping("/event/{id}")
    public EventDTO showEventById(@PathVariable Long id) {
        return iEventService.showEventById(id);
    }

    @GetMapping
    public List<EventDTO> showAllEvents() {
        return iEventService.showAllEvents();
    }

    @GetMapping("/user/event")
    public List<EventDTO> showEventByUserId(@RequestHeader("User-ID") Long userId) {
        return iEventService.showEventByUserId(userId);
    }
}
