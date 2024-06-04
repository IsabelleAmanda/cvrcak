package hr.isabelle.cvrcakapp;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CvrcakLikeTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    void testLike() throws Exception {
        // Arrange
        String json = "{\n" +
                "\t\"userId\": 9,\n" +
                "\t\"postId\": 10\n" +
                "}";

        // Act
        ResultActions result = mvc.perform(post("/cvrcak/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void testUnlike() throws Exception {
        // Arrange
        String json = "{\n" +
                "\t\"userId\": 9,\n" +
                "\t\"postId\": 10\n" +
                "}";

        // Act
        ResultActions result = mvc.perform(delete("/cvrcak/unlike")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // Assert
        result.andExpect(status().isOk());
    }
}
