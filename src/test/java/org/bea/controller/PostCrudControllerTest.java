package org.bea.controller;

import org.bea.config.CommonControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

class PostCrudControllerTest extends CommonControllerTest {

    @Test
    public void addPostGetTest() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"));
    }

    @Test
    void addPostPostTest() throws Exception {
        MockMultipartFile mockImage = new MockMultipartFile(
                "image",
                "test-image.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );
        mockMvc.perform(
                        multipart("/posts")
                                .file(mockImage)
                                .param("title", "Test Post")
                                .param("content", "java,spring")
                                .param("text", "This is a test post content")
                                .param("tags", "")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    public void getToEditTest() throws Exception {
        mockMvc.perform(get("/posts/550e8400-e29b-41d4-a716-446655440001/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(xpath("/html/body/form/table/tr[1]/td/textarea").string("10 скрытых мест в Италии, которые стоит посетить"));
    }

    @Test
    void editPostGetTest() throws Exception {
        mockMvc.perform(get("/posts/550e8400-e29b-41d4-a716-446655440001"))
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/table/tr[2]/td/p[3]").string("""

                                            #Путешествия\s
                                        \
                        """))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    void addPostGet() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"));
    }

    @Test
    public void testEditPostWithImage() throws Exception {
        UUID postId = UUID.randomUUID();
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        mockMvc.perform(multipart("/posts/{id}", postId)
                        .file(imageFile)
                        .param("title", "Test Title")
                        .param("text", "Test Content")
                        .param("tags", "tag1,tag2")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    public void testEditPostWithValidationError() throws Exception {
        UUID postId = UUID.randomUUID();

        mockMvc.perform(multipart("/posts/{id}", postId)
                        .param("title", "")
                        .param("text", "Test Content")
                        .param("tags", "tag1,tag2")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(view().name("error-page"))
                .andExpect(model().attributeExists("error"));
    }
}
