package bootcamp.proposta.propostas.cartao.request;

import bootcamp.proposta.propostas.cartao.Carteira;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CarteiraResponse {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final String email;

    @JsonProperty
    private final LocalDateTime associadaEm;

    @JsonProperty
    private final String emissor;

    public CarteiraResponse(String id, String email, LocalDateTime associadaEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public Carteira converte() {
        return new Carteira(id, email, associadaEm, emissor);
    }
}
