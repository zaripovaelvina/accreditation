package project.controller;

import lombok.RequiredArgsConstructor;
import org.apache.naming.factory.SendMailFactory;
import org.springframework.web.bind.annotation.*;
import project.dto.*;
import project.manager.ApplicationManager;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationManager manager;

    @RequestMapping("/getAll")
    public ApplicationGetAllResponseDTO getAll() {
        return manager.getAll();
    }

    @RequestMapping("/getById")
    public ApplicationGetByIdResponseDTO getByIdFromParam(@RequestParam long id) {
        return manager.getById(id);
    }

    @RequestMapping("/getById/{id}")
    public ApplicationGetByIdResponseDTO getByIdFromPath(@PathVariable long id) {
        return manager.getById(id);
    }

    @PostMapping("/save")
    public ApplicationSaveResponseDTO save(@RequestBody ApplicationSaveRequestDTO requestDTO) {
        return manager.save(requestDTO); }

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

    @PostMapping("/changeStatus")
    public void changeStatusParam(@RequestParam long id, @RequestParam int status) {
        manager.changeStatus(id, status);
    }

    @RequestMapping("/changeStatus/{id}")
    public void changeStatusPath(@PathVariable long id, @PathVariable int status) {
        manager.changeStatus(id, status); }
}

