package org.bea.integration;

import org.bea.config.DataSourceConfigurationTest;
import org.bea.config.WebConfigurationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringJUnitConfig(classes = {DataSourceConfigurationTest.class, WebConfigurationTest.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application.properties")
class AddFindPostControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testAddPost() throws Exception {
        // Создаем mock файл для загрузки
        MockMultipartFile mockImage = new MockMultipartFile(
                "image", // имя параметра должно совпадать с именем в PostRequest
                "test-image.jpg", // имя файла
                "image/jpeg", // content type
                "test image content".getBytes() // содержимое файла
        );
        mockMvc.perform(
                        multipart("/posts") // используем multipart для формы с файлом
                                .file(mockImage) // добавляем файл
                                .param("title", "Test Post") // добавляем обычные параметры
                                .param("content", "java,spring")
                                .param("text", "This is a test post content")
                                .param("tags", "")
                )
                .andExpect(status().is3xxRedirection()) // ожидаем редирект
                .andExpect(redirectedUrl("/posts")); // ожидаем URL редиректа
    }


    @Test
    void posts_add() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk());
    }


}
