package hr.isabelle.cvrcakapp;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CvrcakLikeTests {
    @Autowired
    private MockMvc mvc;
    private static final int userId = 47;
    private static String token;

    private static final String username = "do_not_delete";
    private static final String password = "1234";

    private String getToken(String username, String password) throws Exception {
        final String loginBody = "{" +
                "\"username\": \"" + username + "\",\n" +
                "\"password\": \"" + password + "\"\n" +
                "}";

        ResultActions loginResult = mvc.perform(post("/cvrcak/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(loginBody));

        MvcResult mvcResult = loginResult.andReturn();
        return JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.token");
    }

    @Test
    @Order(1)
    void testLike() throws Exception {
        // Arrange
        String json = "{\n" +
                "\t\"userId\": " + userId + ",\n" +
                "\t\"postId\": 1\n" +
                "}";

        token = getToken(username, password);

        // Act
        ResultActions result = mvc.perform(post("/cvrcak/like")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void testUnlike() throws Exception {
        // Arrange
        String json = "{\n" +
                "\t\"userId\": " + userId + ",\n" +
                "\t\"postId\": 1\n" +
                "}";

        // Act
        ResultActions result = mvc.perform(delete("/cvrcak/unlike")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json));

        // Assert
        result.andExpect(status().isOk());
    }
}
