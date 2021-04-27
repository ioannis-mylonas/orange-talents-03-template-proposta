package bootcamp.proposta.propostas.dados;

import bootcamp.proposta.config.TestConfig;
import bootcamp.proposta.propostas.EstadoProposta;
import bootcamp.proposta.propostas.Proposta;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
@Transactional @Rollback
class AnalisaDadosTest {
    @Autowired private EntityManager entityManager;
    @Autowired private AnalisaDados analisaDados;
    @Autowired private AnaliseClient client;

    @Test
    public void testaDadosSemRestricao() {
        AnaliseResponse response = Mockito.mock(AnaliseResponse.class);
        Mockito.when(client.consulta(Mockito.any())).thenReturn(response);
        Mockito.when(response.getResultadoSolicitacao()).thenReturn(ElegibilidadeDados.SEM_RESTRICAO);

        Proposta proposta = new Proposta("694.782.130-40",
                "meu@email.com", "Nome",
                "Rua dos testes, n.0", BigDecimal.valueOf(500.00),
                EstadoProposta.ANALISE);

        entityManager.persist(proposta);
        analisaDados.analisa();

        assertEquals(ElegibilidadeDados.SEM_RESTRICAO.getEstadoProposta(), proposta.getEstadoProposta());
    }

    @Test
    public void testaDadosComRestricao() {
        FeignException.UnprocessableEntity ex = Mockito.mock(FeignException.UnprocessableEntity.class);
        Mockito.when(client.consulta(Mockito.any())).thenThrow(ex);

        Proposta proposta = new Proposta("694.782.130-40",
                "meu@email.com", "Nome",
                "Rua dos testes, n.0", BigDecimal.valueOf(500.00),
                EstadoProposta.ANALISE);

        entityManager.persist(proposta);
        analisaDados.analisa();

        assertEquals(ElegibilidadeDados.COM_RESTRICAO.getEstadoProposta(), proposta.getEstadoProposta());
    }
}