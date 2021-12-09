package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.dto.PersonGetAllResponseDTO;
import project.dto.PersonGetByIdResponseDTO;
import project.dto.PersonSaveRequestDTO;
import project.dto.PersonSaveResponseDTO;
import project.manager.PersonManager;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonManager manager;

    @RequestMapping("/getAll")
    public PersonGetAllResponseDTO getAll() {
        return manager.getAll();
    }

    @RequestMapping("/getById")
    public PersonGetByIdResponseDTO getByIdFromParam(@RequestParam long id) {
        return manager.getById(id);
    }

    @RequestMapping("/getById/{id}")
    public PersonGetByIdResponseDTO getByIdFromPath(@PathVariable long id) {
        return manager.getById(id);
    }

    @PostMapping("/save")
    public PersonSaveResponseDTO save(@RequestBody PersonSaveRequestDTO requestDTO) {
        return manager.save(requestDTO);
    }

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