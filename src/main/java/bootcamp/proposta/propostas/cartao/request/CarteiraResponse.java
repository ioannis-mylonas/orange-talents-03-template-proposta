package bootcamp.proposta.propostas.cartao.request;

import bootcamp.proposta.propostas.cartao.Cartao;
import bootcamp.proposta.propostas.cartao.carteira.Carteira;
import bootcamp.proposta.propostas.cartao.carteira.CarteiraEnum;
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
    private final CarteiraEnum emissor;

    public CarteiraResponse(String id, String email, LocalDateTime associadaEm, CarteiraEnum emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public Carteira converte(Cartao cartao) {
        return new Carteira(id, emissor, cartao);
    }
}
