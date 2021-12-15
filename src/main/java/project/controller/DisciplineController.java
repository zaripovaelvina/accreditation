package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.dto.*;
import project.manager.DisciplineManager;


@RestController
@RequestMapping("/discipline")
@RequiredArgsConstructor
public class DisciplineController {
    private final DisciplineManager manager;

    @RequestMapping("/getAll")
    public DisciplineGetAllResponseDTO getAll() { return manager.getAll(); }

    @RequestMapping("/getById")
    public DisciplineGetByIdResponseDTO getByIdFromParam(@RequestParam long id) {
        return manager.getById(id);
    }

    @RequestMapping("/getById/{id}")
    public DisciplineGetByIdResponseDTO getByIdFromPath(@PathVariable long id) {
        return manager.getById(id);
    }

    @PostMapping("/save")
    public DisciplineSaveResponseDTO save(@RequestBody DisciplineSaveRequestDTO requestDTO) { return manager.save(requestDTO); }

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

