package bootcamp.proposta.propostas.cartao.bloqueio;

import bootcamp.proposta.config.TestConfig;
import bootcamp.proposta.propostas.cartao.Cartao;
import bootcamp.proposta.propostas.cartao.CartaoClient;
import bootcamp.proposta.propostas.cartao.CartaoEstado;
import bootcamp.proposta.propostas.cartao.Vencimento;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
@Transactional @Rollback
class BloqueioControllerTest {
    @Autowired private MockMvc mvc;
    @PersistenceContext private EntityManager entityManager;

    @Autowired private CartaoClient client;

    private final Cartao cartao = new Cartao("idcartao", LocalDateTime.now(),
            "Titular", List.of(),
            500, null, new Vencimento(),
            CartaoEstado.NORMAL);

    @Test
    @WithMockUser(authorities = {"SCOPE_cartoes:write"})
    public void testaPostSucesso() throws Exception {
        entityManager.persist(cartao);

        Mockito.when(client.bloqueia(Mockito.anyString(), Mockito.any()).getResultado())
                .thenReturn(CartaoEstado.BLOQUEADO);

        mvc.perform(post("/api/cartoes/{id}/bloqueios", cartao.getId())
                .header("User-Agent", "test"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/api/cartoes/idcartao/bloqueios/{[0-9]+}"));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_cartoes:write"})
    public void testaPostUnprocessable() throws Exception {
        cartao.bloqueia(new Bloqueio(cartao, "ip", "teste"));
        entityManager.persist(cartao);

        mvc.perform(post("/api/cartoes/{id}/bloqueios", cartao.getId())
                .header("User-Agent", "test"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_cartoes:write"})
    public void testaPostNotFound() throws Exception {
        mvc.perform(post("/api/cartoes/{id}/bloqueios", cartao.getId())
                .header("User-Agent", "test"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}