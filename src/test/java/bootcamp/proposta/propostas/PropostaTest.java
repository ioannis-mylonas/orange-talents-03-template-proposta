package bootcamp.proposta.propostas;

import bootcamp.proposta.config.TestConfig;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
class PropostaTest {
    @ParameterizedTest
    @CsvSource({"055.346.430-21,05534643021", "888.467.370-40,88846737040",
            "976.455.960-31,97645596031", "146.666.070-80,14666607080",
            "55.323.426/0001-78,55323426000178", "91.309.125/0001-53,91309125000153"})
    public void testaFormatacaoDocumento(String documento, String esperado) {
        String resultado = Proposta.formataDocumento(documento);
        assertEquals(esperado, resultado);
    }
}