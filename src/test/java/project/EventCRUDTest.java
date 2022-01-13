package project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Path;

@Testcontainers
@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class EventCRUDTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile()
    );

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformCRUD() throws Exception {
        // getAll
        mockMvc.perform(
                MockMvcRequestBuilders.get("/event/getAll")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                             "events": [
                                               {
                                                 "id": 1,
                                                 "name": "Чемпионат мира по стрельбе",
                                                 "image": "noimage.png"
                                               },
                                               {
                                                 "id": 2,
                                                 "name": "Чемпионат мира по биатлону",
                                                 "image": "noimage.png"
                                               },
                                               {
                                                 "id": 3,
                                                 "name": "Чемпионат по практической стрельбе",
                                                 "image": "noimage.png"
                                               },
                                               {
                                                 "id": 4,
                                                 "name": "Чемпионат мира по футболу",
                                                 "image": "noimage.png"
                                               }
                                             ]
                                        }                                                        
                                        """
                        )
                );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/event/getById")
                                .param("id", String.valueOf(1))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                           "events": {
                                             "id": 1,
                                             "name": "Чемпионат мира по стрельбе",
                                             "image": "noimage.png"
                                           }
                                        }                                                                   
                                        """
                        )
                );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/event/getById{id}", 1)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                           "events": {
                                             "id": 1,
                                             "name": "Чемпионат мира по стрельбе",
                                             "image": "noimage.png"
                                           }
                                        }                                                               
                                        """
                        )
                );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/event/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                // language=JSON
                                """
                                        {
                                          "id": 0,
                                          "name": "Практическая стрельба 2022",
                                          "image": "ee2e2796-8c64-4d76-a015-0d5259898141.jpeg"
                                        }
                                        """
                        )
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "events": {
                                            "id": 5,
                                            "name": "Практическая стрельба 2022",
                                            "image": "ee2e2796-8c64-4d76-a015-0d5259898141.jpeg"
                                          }
                                        }
                                        """
                        )
                );
    }
}
