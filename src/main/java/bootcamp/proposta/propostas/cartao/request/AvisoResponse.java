package bootcamp.proposta.propostas.cartao.request;

import bootcamp.proposta.propostas.cartao.Cartao;
import bootcamp.proposta.propostas.cartao.aviso.Aviso;
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

    public Aviso converte(Cartao cartao, String ip, String userAgent) {
        return new Aviso(cartao, destino, validoAte, ip, userAgent);
    }
}
