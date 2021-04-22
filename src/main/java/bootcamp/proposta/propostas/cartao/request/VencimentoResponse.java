package bootcamp.proposta.propostas.cartao.request;

import bootcamp.proposta.propostas.cartao.Vencimento;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class VencimentoResponse {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final int dia;

    @JsonProperty
    private final LocalDateTime dataDeCriacao;

    public VencimentoResponse(String id, int dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Vencimento converte() {
        return new Vencimento(id, dia, dataDeCriacao);
    }
}
