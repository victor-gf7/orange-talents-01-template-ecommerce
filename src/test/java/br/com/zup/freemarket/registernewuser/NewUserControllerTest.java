package br.com.zup.freemarket.registernewuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NewUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager manager;

    @Test
    void mustRegisterUser() throws Exception {
        NewUserRequest user = new NewUserRequest("user@email.com", "123456");

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void mustNotRegisterUserWithInvalidEmail() throws Exception {
        NewUserRequest user = new NewUserRequest("useremail.com", "123456");

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'login')].error").value("must be a well-formed email address"));
    }

    @Test
    void mustNotRegisterUserWithNullEmailAndPassword() throws Exception {
        NewUserRequest user = new NewUserRequest(null, null);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'login')].error").value("must not be blank"))
                .andExpect(jsonPath("$[?(@.field == 'password')].error").value("must not be blank"));

    }

    @Test
    void mustNotRegisterUserWithPasswordLesThenSix() throws Exception {
        NewUserRequest user = new NewUserRequest("user@email.com", "12345");

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'password')].error").value("size must be between 6 and 2147483647"));

    }

    @Test
    @Transactional
    void mustNotAllowDuplicateEmailRegistration() throws Exception {
        Usuario user1 = new Usuario("duplicatedtest@email.com", "123456");

        manager.persist(user1);

        NewUserRequest userDuplicated = new NewUserRequest("duplicatedtest@email.com", "123456");

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDuplicated))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'login')].error").value("Já existe esse valor cadastrado"));

    }

}