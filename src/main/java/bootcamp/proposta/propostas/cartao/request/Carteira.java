package bootcamp.proposta.propostas.cartao.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Carteira {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final String email;

    @JsonProperty
    private final LocalDateTime associadaEm;

    @JsonProperty
    private final String emissor;

    public Carteira(String id, String email, LocalDateTime associadaEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }

    public String getEmissor() {
        return emissor;
    }
}
