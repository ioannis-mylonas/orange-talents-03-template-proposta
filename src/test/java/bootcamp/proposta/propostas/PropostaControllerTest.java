package bootcamp.proposta.propostas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
class PropostaControllerTest {
    @Autowired private EntityManager entityManager;
    @Mock private Proposta search;

    @Autowired private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testaPostSucesso() throws Exception {
        PropostaRequest request = new PropostaRequest("775.907.210-42",
                "teste@cliente.com", "Cliente",
                "Rua dos testes, n.0", BigDecimal.valueOf(0.00));
        String json = mapper.writeValueAsString(request);

        mvc.perform(post("/propostas")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/propostas/{\\d+}"));
    }

    @Test
    public void testaPostFalha() throws Exception {
        PropostaRequest request = new PropostaRequest("",
                "", "", "", BigDecimal.valueOf(0.00));
        String json = mapper.writeValueAsString(request);

        mvc.perform(post("/propostas")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testaGetPropostaInexistenteNotFound() throws Exception {
        mvc.perform(get("/propostas/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Transactional @Rollback
    public void testaGetPropostaExistenteSucesso() throws Exception {
        Proposta proposta = new Proposta("296.271.840-04",
                "outro@email.test", "Teste",
                "Rua dos testes, n.0", BigDecimal.valueOf(0.00));
        entityManager.persist(proposta);

        String json = mapper.writeValueAsString(proposta);
        mvc.perform(get("/propostas/{id}", proposta.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}