package bootcamp.proposta.propostas.cartao.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Aviso {
    @JsonProperty
    private final LocalDate validoAte;

    @JsonProperty
    private final String destino;

    public Aviso(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }
}
