package project.manager;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.dto.UploadSingleMediaResponseDTO;
import project.exception.UnsupportedMediaException;
import project.exception.UploadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class MediaManager {
    private final Path path = Path.of("media");

    public MediaManager() throws IOException {
        Files.createDirectories(path);
    }

    public UploadSingleMediaResponseDTO save(MultipartFile file) {
        try {
            final String name = UUID.randomUUID() + getExtension(file.getContentType());
            file.transferTo(path.resolve(name));
            return new UploadSingleMediaResponseDTO(name);
        } catch (IOException e) {
            throw new UploadException(e);
        }
    }

    private String getExtension(String contentType) {
        if (contentType.equals("image/jpeg")) {
            return ".jpg";
        }

        throw new UnsupportedMediaException();
    }
}
