package br.com.zup.freemarket.registernewcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NewCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager manager;

    @Test
    void mustRegisterCategoryWithoutMotherCategory() throws Exception {
        NewCategoryRequest request = new NewCategoryRequest("Tecnologia", null);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @Transactional
    void mustRegisterCategoryWithMotherCategory() throws Exception {

        Category category = new Category("Eletrodomésticos", null);

        manager.persist(category);

        NewCategoryRequest request = new NewCategoryRequest("Cozinha", 1L);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void mustNotRegisterCategoryWithoutName() throws Exception {

        NewCategoryRequest request = new NewCategoryRequest("", null);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("[?(@.field == 'name')].error").value("must not be blank"));

    }

    @Test
    @Transactional
    void mustNotRegisterCategoryWithSameName() throws Exception {

        Category category = new Category("Decoração", null);

        manager.persist(category);

        NewCategoryRequest request = new NewCategoryRequest("Decoração", null);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("[?(@.field == 'name')].error").value("Já existe esse valor cadastrado"));

    }

    @Test
    void mustNotRegisterCategoryIfThereIsNoMotherCategoryId() throws Exception {

        NewCategoryRequest request = new NewCategoryRequest("Celular", 999L);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("[?(@.field == 'idMotherCategory')].error").value("Não existe registros com o ID informado"));

    }
}