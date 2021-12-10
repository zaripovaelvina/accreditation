package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.dto.*;
import project.manager.EventManager;


@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final EventManager manager;

    @RequestMapping("/getAll")
    public EventGetAllResponseDTO getAll() {
        return manager.getAll();
    }

    @RequestMapping("/getById")
    public EventGetByIdResponseDTO getByIdFromParam(@RequestParam long id) {
        return manager.getById(id);
    }

    @RequestMapping("/getById/{id}")
    public EventGetByIdResponseDTO getByIdFromPath(@PathVariable long id) {
        return manager.getById(id);
    }

    @PostMapping("/save")
    public EventSaveResponseDTO save(@RequestBody EventSaveRequestDTO requestDTO) { return manager.save(requestDTO); }

    @PostMapping("/removeById")
    public void removeByIdFromParam(@RequestParam long id) {
        manager.removeById(id);
    }

    @PostMapping("/removeById/{id}")
    public void removeByIdFromPath(@PathVariable long id) {
        manager.removeById(id);
    }

    @PostMapping("/restoreById")
    public void restoreByIdFromParam(@RequestParam long id) {
        manager.restoreById(id);
    }

    @PostMapping("/restoreById/{id}")
    public void restoreByIdFromPath(@PathVariable long id) {
        manager.restoreById(id);
    }
}

