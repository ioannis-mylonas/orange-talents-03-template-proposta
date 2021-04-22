package bootcamp.proposta.propostas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PropostaRequestTest {
    @Autowired private ApplicationContext context;
    private LocalValidatorFactoryBean validator;
    private PropostaRequestBuilder builder = new PropostaRequestBuilder();

    @BeforeEach
    public void setup() {
        SpringConstraintValidatorFactory factory = new SpringConstraintValidatorFactory(context.getAutowireCapableBeanFactory());

        validator = new LocalValidatorFactoryBean();
        validator.setConstraintValidatorFactory(factory);
        validator.setApplicationContext(context);
        validator.afterPropertiesSet();

        builder.setDocumento("206.257.010-46")
                .setNome("Cliente")
                .setEmail("emaildos@clientes.com")
                .setEndereco("Rua dos Clientes, n.0")
                .setSalario(0.00);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "123456", "894.936.120-5", "89.936.120-58", "894.96.120-58", "894.936.12-58"
    })
    public void testaDocumentoInvalido(String documento) {
        PropostaRequest request = builder.setDocumento(documento).build();
        Set<ConstraintViolation<PropostaRequest>> errors = validator.validate(request);
        assertFalse(errors.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testaNomeVazio(String nome) {
        PropostaRequest request = builder.setNome(nome).build();
        Set<ConstraintViolation<PropostaRequest>> errors = validator.validate(request);
        assertFalse(errors.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "emailinválido", "EMAIL INVÁLIDO", "não é um e-mail", "NO@", "@", "no email@email.com"
    })
    public void testaEmailInvalido(String email) {
        PropostaRequest request = builder.setEmail(email).build();
        Set<ConstraintViolation<PropostaRequest>> errors = validator.validate(request);
        assertFalse(errors.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testaEnderecoInvalido(String endereco) {
        PropostaRequest request = builder.setEndereco(endereco).build();
        Set<ConstraintViolation<PropostaRequest>> errors = validator.validate(request);
        assertFalse(errors.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = { -0.01, -1.00, -5.00, -10.00 })
    public void testaSalarioInvalido(Double salario) {
        PropostaRequest request = builder.setSalario(salario).build();
        Set<ConstraintViolation<PropostaRequest>> errors = validator.validate(request);
        assertFalse(errors.isEmpty());
    }

    @Test
    public void testaRequestValida() {
        PropostaRequest request = builder.build();
        Set<ConstraintViolation<PropostaRequest>> errors = validator.validate(request);
        assertTrue(errors.isEmpty());
    }
}