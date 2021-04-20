package bootcamp.proposta.propostas.cartao.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Parcela {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final int quantidade;

    @JsonProperty
    private final BigDecimal valor;

    public Parcela(String id, int quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
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
}
