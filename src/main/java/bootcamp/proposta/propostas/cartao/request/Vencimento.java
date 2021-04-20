package bootcamp.proposta.propostas.cartao.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Vencimento {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final int dia;

    @JsonProperty
    private final LocalDateTime dataDeCriacao;

    public Vencimento(String id, int dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }
}
