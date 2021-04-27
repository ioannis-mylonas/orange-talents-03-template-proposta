package bootcamp.proposta;

import bootcamp.proposta.config.TestConfig;
import bootcamp.proposta.propostas.cartao.CartaoClient;
import bootcamp.proposta.propostas.dados.AnaliseClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Import(TestConfig.class)
class PropostaApplicationTests {

	@Test
	void contextLoads() {
	}

}
