package bootcamp.proposta.propostas.cartao.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Renegociacao {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final int quantidade;

    @JsonProperty
    private final BigDecimal valor;

    @JsonProperty
    private final LocalDateTime dataDeCriacao;

    public Renegociacao(String id, int quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }
}
