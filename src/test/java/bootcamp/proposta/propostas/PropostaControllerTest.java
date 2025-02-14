package bootcamp.proposta.propostas;

import bootcamp.proposta.config.TestConfig;
import bootcamp.proposta.propostas.cartao.CartaoClient;
import bootcamp.proposta.propostas.dados.AnaliseClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Import(TestConfig.class)
class PropostaControllerTest {
    @Autowired private EntityManager entityManager;
    @Mock private Proposta search;

    @Autowired private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser(authorities = {"SCOPE_propostas:write"})
    public void testaPostSucesso() throws Exception {
        PropostaRequest request = new PropostaRequest("775.907.210-42",
                "teste@cliente.com", "Cliente",
                "Rua dos testes, n.0", BigDecimal.valueOf(0.00));
        String json = mapper.writeValueAsString(request);

        mvc.perform(post("/api/propostas")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/propostas/{\\d+}"));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_propostas:write"})
    public void testaPostFalha() throws Exception {
        PropostaRequest request = new PropostaRequest("",
                "", "", "", BigDecimal.valueOf(0.00));
        String json = mapper.writeValueAsString(request);

        mvc.perform(post("/api/propostas")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_propostas:read"})
    public void testaGetPropostaInexistenteNotFound() throws Exception {
        mvc.perform(get("/api/propostas/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_propostas:read"})
    @Transactional @Rollback
    public void testaGetPropostaExistenteSucesso() throws Exception {
        Proposta proposta = new Proposta("296.271.840-04",
                "outro@email.test", "Teste",
                "Rua dos testes, n.0", BigDecimal.valueOf(0.00),
                EstadoProposta.ANALISE);

        entityManager.persist(proposta);

        mvc.perform(get("/api/propostas/{id}", proposta.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_propostas:write"})
    @Transactional @Rollback
    public void testaPostPropostaDocumentoExistenteUnprocessable() throws Exception {
        PropostaRequest proposta = new PropostaRequest("296.271.840-04",
                "outro@email.test", "Teste",
                "Rua dos testes, n.0", BigDecimal.valueOf(0.00));
        entityManager.persist(proposta.converte());

        String json = mapper.writeValueAsString(proposta);
        mvc.perform(post("/api/propostas")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
}