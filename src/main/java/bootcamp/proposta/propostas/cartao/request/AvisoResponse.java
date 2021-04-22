package bootcamp.proposta.propostas.cartao.request;

import bootcamp.proposta.propostas.cartao.Aviso;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class AvisoResponse {
    @JsonProperty
    private final LocalDate validoAte;

    @JsonProperty
    private final String destino;

    public AvisoResponse(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public Aviso converte() {
        return new Aviso(validoAte, destino);
    }
}
