package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.PersonGetAllResponseDTO;
import project.manager.PersonManager;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final PersonManager manager;

    @RequestMapping("/getAll")
    public PersonGetAllResponseDTO getAll() {
        return manager.getAll();
    }
}
