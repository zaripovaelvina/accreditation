package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.dto.PersonGetAllResponseDTO;
import project.dto.PersonGetByIdResponseDTO;
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

    @RequestMapping("/getById{id}")
    public PersonGetByIdResponseDTO getByIdFromPath(@PathVariable long id) {
        return manager.getById(id);
    }
}
