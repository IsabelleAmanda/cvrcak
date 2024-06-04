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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CvrcakUserTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void testCreateUser() throws Exception {
        // Arrange
        String json = "{\n" +
                "    \"username\": \"peroperic123\",\n" +
                "    \"firstName\": \"Pero\",\n" +
                "    \"lastName\": \"Peric\",\n" +
                "    \"password\": \"thishastobeencoded\",\n" +
                "    \"email\": \"pero.peric@email.com\",\n" +
                "    \"country\": \"Slovenia\",\n" +
                "    \"gender\": \"M\",\n" +
                "    \"birthday\": \"2003-01-02\",\n" +
                "    \"image\": null,\n" +
                "    \"registerDate\": \"2024-05-28\"\n" +
                "}";

        // Act
        ResultActions result = mockMvc.perform(post("/cvrcak/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));

        // Assert
        // String address = "http://localhost/cvrcak/user/peroperic123";

        result.andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void testGetUser() throws Exception {
        // Arrange
        String username = "john_doe";

        // Act
        ResultActions result = mockMvc.perform(get("/cvrcak/user/{username}", username));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    private int getUserId(String username) throws Exception {
        ResultActions result = mockMvc.perform(get("/cvrcak/user/{username}", username));

        MvcResult mvcResult = result.andReturn();
        int id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.userId");

        return id;
    }

    @Test
    @Order(3)
    void testUpdateUser() throws Exception {
        // Arrange
        int oldId = getUserId("peroperic123");
        String newUsername = "anaanic123";

        String json = "{\n" +
                "    \"userId\":" + oldId + ",\n" +
                "    \"username\": \"" + newUsername + "\",\n" +
                "    \"firstName\": \"Ana\",\n" +
                "    \"lastName\": \"Anic\",\n" +
                "    \"password\": \"thishastobeencoded\",\n" +
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
                .content(json));

        ResultActions updatedUserResult = mockMvc.perform(get("/cvrcak/user/{username}", newUsername));

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
        String json = "{\"userId\":" + id + "}";

        // Act
        ResultActions result = mockMvc.perform(delete("/cvrcak/user/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // Assert
        result.andExpect(status().isOk());
    }

}
