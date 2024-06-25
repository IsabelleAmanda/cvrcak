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

import javax.xml.transform.Result;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CvrcakUserTests {
    @Autowired
    private MockMvc mockMvc;
    private static final Random random = new Random();

    private static String token;

    @Test
    @Order(1)
    void testCreateUser() throws Exception {
        // Arrange
        String json = "{\n" +
                "    \"username\": \"peroperic123\",\n" +
                "    \"firstName\": \"Pero\",\n" +
                "    \"lastName\": \"Peric\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"email\": \"pero.peric" + random.nextInt() + "@email.com\",\n" +
                "    \"country\": \"Slovenia\",\n" +
                "    \"gender\": \"M\",\n" +
                "    \"birthday\": \"2003-01-02\",\n" +
                "    \"image\": null,\n" +
                "    \"registerDate\": \"2024-05-28\"\n" +
                "}";

        // Act
        ResultActions result = mockMvc.perform(post("/cvrcak/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));

        // Assert
        result.andExpect(status().isOk());

        token = getToken("peroperic123", "password");
        // System.out.println(token);
    }

    @Test
    @Order(2)
    void testGetUser() throws Exception {
        // Arrange
        String username = "peroperic123";

        // Act
        ResultActions result = mockMvc.perform(get("/cvrcak/user/{username}", username)
                .header("Authorization", "Bearer " + token));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.firstName").value("Pero"))
                .andExpect(jsonPath("$.lastName").value("Peric"));
    }

    private String getToken(String username, String password) throws Exception {
        final String loginBody = "{" +
                "\"username\": \"" + username + "\",\n" +
                "\"password\": \"" + password + "\"\n" +
                "}";

        ResultActions loginResult = mockMvc.perform(post("/cvrcak/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(loginBody));

        MvcResult mvcResult = loginResult.andReturn();
        return JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.token");
    }

    private int getUserId(String username) throws Exception {
        ResultActions result = mockMvc.perform(get("/cvrcak/user/{username}", username)
                                .header("Authorization", "Bearer " + token));

        MvcResult mvcResult = result.andReturn();
        int id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.userId");

        return id;
    }

    @Test
    @Order(3)
    void testUpdateUser() throws Exception {
        // Arrange
        String oldUsername = "peroperic123";
        int oldId = getUserId(oldUsername);
        String newUsername = "anaanic123";

        String json = "{\n" +
                "    \"userId\":" + oldId + ",\n" +
                "    \"username\": \"" + newUsername + "\",\n" +
                "    \"firstName\": \"Ana\",\n" +
                "    \"lastName\": \"Anic\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"email\": \"ana.anic@email.com\",\n" +
                "    \"country\": \"Croatia\",\n" +
                "    \"gender\": \"F\",\n" +
                "    \"birthday\": \"2003-01-02\",\n" +
                "    \"image\": null,\n" +
                "    \"registerDate\": \"2024-05-28\"\n" +
                "}";

        // Act
        ResultActions result = mockMvc.perform(put("/cvrcak/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json));

        token = getToken(newUsername, "password");

        ResultActions updatedUserResult = mockMvc.perform(get("/cvrcak/user/{username}", newUsername)
                .header("Authorization", "Bearer " + token));

        // Assert
        updatedUserResult.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("anaanic123"))
                .andExpect(jsonPath("$.firstName").value("Ana"))
                .andExpect(jsonPath("$.lastName").value("Anic"));
    }

    @Test
    @Order(4)
    void testDeleteUser() throws Exception {
        // Arrange
        int id = getUserId("anaanic123");

        // Act
        ResultActions result = mockMvc.perform(delete("/cvrcak/user/delete/id/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token));

        // Assert
        result.andExpect(status().isOk());
    }

}
