package bootcamp.proposta.propostas.cartao.carteira;

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
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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
@AutoConfigureDataJpa
@Import(TestConfig.class)
@Transactional @Rollback
class CarteiraControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private CartaoClient client;
    @PersistenceContext private EntityManager entityManager;

    private static final String DEFAULT_EMAIL = "meu@email.com";
    private final ObjectMapper mapper = new ObjectMapper();

    private final Cartao cartao = new Cartao("idcartao", LocalDateTime.now(),
            "Titular", List.of(),
            500, null, null,
            CartaoEstado.NORMAL);

    @Test
    @WithMockUser(authorities = {"SCOPE_cartoes:write"})
    public void testaPostSucesso() throws Exception {
        entityManager.persist(cartao);

        Mockito.when(client.associa(Mockito.anyString(), Mockito.any()).converte(Mockito.any(), Mockito.any()))
                .thenReturn(new Carteira("idcarteira", CarteiraEnum.PAYPAL, cartao));

        ClientCarteiraRequest request = new ClientCarteiraRequest(CarteiraEnum.PAYPAL, DEFAULT_EMAIL);
        String json = mapper.writeValueAsString(request);

        mvc.perform(post("/api/cartoes/{id}/carteiras", cartao.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/api/cartoes/idcartao/carteiras/{[0-9]+}"));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_cartoes:write"})
    public void testaPostUnprocessableEntity() throws Exception {
        Carteira carteira = new Carteira("idcarteira", CarteiraEnum.PAYPAL, cartao);
        cartao.addCarteira(carteira);
        entityManager.persist(cartao);
        entityManager.persist(carteira);

        Mockito.when(client.associa(Mockito.anyString(), Mockito.any()).converte(Mockito.any(), Mockito.any()))
                .thenReturn(new Carteira("idcarteira", CarteiraEnum.PAYPAL, cartao));

        ClientCarteiraRequest request = new ClientCarteiraRequest(CarteiraEnum.PAYPAL, DEFAULT_EMAIL);
        String json = mapper.writeValueAsString(request);

        mvc.perform(post("/api/cartoes/{id}/carteiras", cartao.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_cartoes:write"})
    public void testaPostNotFound() throws Exception {
        ClientCarteiraRequest request = new ClientCarteiraRequest(CarteiraEnum.PAYPAL, DEFAULT_EMAIL);
        String json = mapper.writeValueAsString(request);

        mvc.perform(post("/api/cartoes/{id}/carteiras", cartao.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}