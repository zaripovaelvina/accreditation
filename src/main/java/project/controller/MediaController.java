package project.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.dto.UploadSingleMediaResponseDTO;
import project.manager.MediaManager;

@RestController
@RequestMapping("/media")
@AllArgsConstructor
public class MediaController {
    private final MediaManager manager;

    @RequestMapping("/multipart")
    public UploadSingleMediaResponseDTO upload(@RequestPart MultipartFile image) {
        return manager.save(image);
    }

}
