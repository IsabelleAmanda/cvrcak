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
public class CvrcakPostTests {
    @Autowired
    private MockMvc mvc;
    private static int userId;
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

    private int getUserId(String username) throws Exception {
        ResultActions result = mvc.perform(get("/cvrcak/user/{username}", username)
                .header("Authorization", "Bearer " + token));

        MvcResult mvcResult = result.andReturn();
        int id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.userId");

        return id;
    }

    @Test
    @Order(1)
    void testCreatePost() throws Exception {
        // Arrange
        token = getToken(username, password);
        userId = getUserId(username);

        String json = "{\n" +
                "\t\"userId\": " + userId + ",\n" +
                "\t\"title\": \"If i could just kill a man\",\n" +
                "\t\"content\": \"naahhhh jkjk\",\n" +
                "\t\"image\": \"\",\n" +
                "\t\"isPublic\": true,\n" +
                "\t\"isPermanent\": false,\n" +
                "\t\"isDeleted\": false,\n" +
                "\t\"disappearTime\": null\n" +
                "}";

        // Act
        ResultActions result = mvc.perform(post("/cvrcak/post/newPost")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void testGetAllPosts() throws Exception {
        // Arrange
        // Act
        ResultActions result = mvc.perform(get("/cvrcak/post/all")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void updatePost() throws Exception {
        // Arrange
        ResultActions resultPost = mvc.perform(get("/cvrcak/user/{userId}/posts", userId)
                .header("Authorization", "Bearer " + token));

        MvcResult mvcResult = resultPost.andReturn();
        int id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$[-1].postId");

        String json = "{\n" +
                "\t\"postId\": " + id + ",\n" +
                "\t\"title\": \"I'm not gay\",\n" +
                "\t\"content\": \"Not that there's anything wrong with that\",\n" +
                "\t\"image\": null\n" +
                "}";

        // Act
        ResultActions result = mvc.perform(put("/cvrcak/post/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void testDeletePost() throws Exception {
        // Arrange
        // Obtaining the last post made by our user
        ResultActions resultPost = mvc.perform(get("/cvrcak/user/{userId}/posts", userId)
                .header("Authorization", "Bearer " + token));

        MvcResult mvcResult = resultPost.andReturn();
        int id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$[-1].postId");

        // Act
        ResultActions result = mvc.perform(delete("/cvrcak/post/delete/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token));

        // Assert
        result.andExpect(status().isOk());
    }
}
