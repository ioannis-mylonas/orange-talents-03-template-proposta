package bootcamp.proposta.propostas.cartao.request;

import bootcamp.proposta.propostas.cartao.Bloqueio;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class BloqueioResponse {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final LocalDateTime bloqueadoEm;

    @JsonProperty
    private final String sistemaResponsavel;

    @JsonProperty
    private final boolean ativo;

    public BloqueioResponse(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }

    public Bloqueio converte() {
        return new Bloqueio(id, bloqueadoEm, sistemaResponsavel, ativo);
    }
}
