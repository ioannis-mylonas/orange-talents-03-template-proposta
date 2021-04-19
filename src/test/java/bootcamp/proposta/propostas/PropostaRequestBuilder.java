package bootcamp.proposta.propostas;

import java.math.BigDecimal;

public class PropostaRequestBuilder {
    private String documento, nome, email, endereco;
    private BigDecimal salario;

    public PropostaRequestBuilder setDocumento(String documento) {
        this.documento = documento;
        return this;
    }

    public PropostaRequestBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public PropostaRequestBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public PropostaRequestBuilder setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public PropostaRequestBuilder setSalario(Double salario) {
        this.salario = salario == null ? null : BigDecimal.valueOf(salario);
        return this;
    }

    public PropostaRequestBuilder setSalario(BigDecimal salario) {
        this.salario = salario;
        return this;
    }

    public PropostaRequest build() {
        return new PropostaRequest(documento, email, nome, endereco, salario);
    }
}
