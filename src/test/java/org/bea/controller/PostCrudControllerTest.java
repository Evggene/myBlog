package org.bea.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

class PostCrudControllerTest extends CommonControllerContext{

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
