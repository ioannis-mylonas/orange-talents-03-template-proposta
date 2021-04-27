package bootcamp.proposta.propostas.cartao.aviso;

import bootcamp.proposta.config.TestConfig;
import bootcamp.proposta.propostas.cartao.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
@Transactional @Rollback
class AvisoControllerTest {
    @Autowired private MockMvc mvc;
    @PersistenceContext private EntityManager entityManager;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser(authorities = {"SCOPE_cartoes:write"})
    public void testPostSucesso() throws Exception {
        Cartao cartao = new Cartao("idcartao", LocalDateTime.now(),
                "Titular", List.of(),
                500, null, new Vencimento(),
                CartaoEstado.NORMAL);
        entityManager.persist(cartao);

        ObjectNode request = mapper.createObjectNode();
        request.put("destino", "Passárgada");
        request.put("termino", "01/01/9999");

        String json = request.toString();
        mvc.perform(post("/api/cartoes/{id}/avisos", cartao.getId())
                .header("User-Agent", "Test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/api/cartoes/idcartao/avisos/{[0-9]+}"));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_cartoes:write"})
    public void testPostBadRequest() throws Exception {
        Cartao cartao = new Cartao("idcartao", LocalDateTime.now(),
                "Titular", List.of(),
                500, null, new Vencimento(),
                CartaoEstado.NORMAL);
        entityManager.persist(cartao);

        ObjectNode request = mapper.createObjectNode();
        request.put("destino", "");
        request.put("termino", "data inválida");

        String json = request.toString();
        mvc.perform(post("/api/cartoes/{id}/avisos", cartao.getId())
                .header("User-Agent", "Test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_cartoes:write"})
    public void testPostNotFound() throws Exception {
        ObjectNode request = mapper.createObjectNode();
        request.put("destino", "Passárgada");
        request.put("termino", "01/01/9999");

        String json = request.toString();
        mvc.perform(post("/api/cartoes/idcartao/avisos")
                .header("User-Agent", "Test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}