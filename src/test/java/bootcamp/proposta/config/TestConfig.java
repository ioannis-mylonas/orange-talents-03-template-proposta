package bootcamp.proposta.config;

import bootcamp.proposta.propostas.cartao.CartaoClient;
import bootcamp.proposta.propostas.dados.AnaliseClient;
import org.mockito.Answers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@TestConfiguration
public class TestConfig {
    @MockBean(answer = Answers.RETURNS_DEEP_STUBS) private CartaoClient cartaoClient;
    @MockBean private AnaliseClient analiseClient;
    @MockBean private JwtDecoder jwtDecoder;
}
