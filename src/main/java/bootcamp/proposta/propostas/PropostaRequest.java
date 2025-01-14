package bootcamp.proposta.propostas;

import bootcamp.proposta.validators.CpfOrCnpj;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PropostaRequest {
    @NotBlank
    @CpfOrCnpj
    @JsonProperty
    private final String documento;

    @NotBlank
    @Email
    @JsonProperty
    private final String email;

    @NotBlank
    @JsonProperty
    private final String nome;

    @NotBlank
    @JsonProperty
    private final String endereco;

    @NotNull
    @DecimalMin(value = "0.00")
    @JsonProperty
    private final BigDecimal salario;

    public PropostaRequest(@NotBlank String documento,
                           @NotBlank @Email String email,
                           @NotBlank String nome,
                           @NotBlank String endereco,
                           @NotNull @DecimalMin(value = "0.00") BigDecimal salario) {

        this.documento = Proposta.formataDocumento(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getDocumento() {
        return documento;
    }

    public Proposta converte() {
        return new Proposta(documento, email, nome, endereco, salario, EstadoProposta.ANALISE);
    }
}
