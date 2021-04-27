package bootcamp.proposta.propostas;

import bootcamp.proposta.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
class PropostaRepositoryTest {
    @Autowired private PropostaRepository repository;
    private final PropostaRequestBuilder builder = new PropostaRequestBuilder();

    private static final String DOCUMENTO_DEFAULT = "59.691.763/0001-79";
    private static final String EMAIL_DEFAULT = "email@teste.com";
    private static final String NOME_DEFAULT = "default";
    private static final String ENDERECO_DEFAULT = "Rua dos testes, n.0";
    private static final BigDecimal SALARIO_DEFAULT = BigDecimal.valueOf(2000.00);

    @BeforeEach
    public void setup() {
        builder.setDocumento(DOCUMENTO_DEFAULT);
        builder.setEmail(EMAIL_DEFAULT);
        builder.setNome(NOME_DEFAULT);
        builder.setEndereco(ENDERECO_DEFAULT);
        builder.setSalario(SALARIO_DEFAULT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"30225499000104", "09017073000160", "53336958067", "32268209075"})
    public void testaEncontraPorDocumentoSucesso(String documento) {
        List<String> documentos = List.of(
                "30.225.499/0001-04", "09.017.073/0001-60", "78311740000136", "38892567000192",
                "212.598.190-43", "795.632.010-00", "53336958067", "32268209075"
        );

        List<Proposta> propostas = documentos
                .stream()
                .map(i -> builder.setDocumento(i).build().converte())
                .collect(Collectors.toList());

        repository.saveAll(propostas);
        assertTrue(repository.existsByDocumento(documento));
    }

    @ParameterizedTest
    @ValueSource(strings = {"30.225.499/0001-04", "09.017.073/0001-60", "63898446034",
            "212.598.190-43", "795.632.010-00", "533.369.580-67", "322.682.090-75"})
    public void testaEncontraPorDocumentoFalha(String documento) {
        List<String> documentos = List.of(
                "30.225.499/0001-04", "09.017.073/0001-60", "78311740000136", "38892567000192",
                "212.598.190-43", "795.632.010-00", "53336958067", "32268209075"
        );

        List<Proposta> propostas = documentos
                .stream()
                .map(i -> builder.setDocumento(i).build().converte())
                .collect(Collectors.toList());

        repository.saveAll(propostas);
        assertFalse(repository.existsByDocumento(documento));
    }

    @ParameterizedTest
    @EnumSource(EstadoProposta.class)
    public void testaEncontraSemCartaoEEstadoSucesso(EstadoProposta estado) {
        Proposta proposta = builder.build().converte();
        proposta.atualiza(estado);
        repository.save(proposta);

        assertFalse(repository.findByCartaoNullAndEstadoProposta(estado).isEmpty());
    }

    @Test
    public void testaEncontraSemCartaoEEstadoFalha() {
        Proposta proposta = builder.build().converte();
        proposta.atualiza(EstadoProposta.NAO_ELEGIVEL);
        repository.save(proposta);

        assertTrue(repository.findByCartaoNullAndEstadoProposta(EstadoProposta.ELEGIVEL).isEmpty());
        assertTrue(repository.findByCartaoNullAndEstadoProposta(EstadoProposta.ANALISE).isEmpty());
        assertFalse(repository.findByCartaoNullAndEstadoProposta(EstadoProposta.NAO_ELEGIVEL).isEmpty());
    }

    @ParameterizedTest
    @EnumSource(EstadoProposta.class)
    public void testaEncontraPorEstadoSucesso(EstadoProposta estado) {
        Proposta proposta = builder.build().converte();
        proposta.atualiza(estado);
        repository.save(proposta);

        assertFalse(repository.findByEstadoProposta(estado).isEmpty());
    }

    @Test
    public void testaEncontraPorEstadoFalha() {
        Proposta proposta = builder.build().converte();
        proposta.atualiza(EstadoProposta.ANALISE);
        repository.save(proposta);

        assertTrue(repository.findByEstadoProposta(EstadoProposta.ELEGIVEL).isEmpty());
        assertTrue(repository.findByEstadoProposta(EstadoProposta.NAO_ELEGIVEL).isEmpty());
        assertFalse(repository.findByEstadoProposta(EstadoProposta.ANALISE).isEmpty());
    }
}