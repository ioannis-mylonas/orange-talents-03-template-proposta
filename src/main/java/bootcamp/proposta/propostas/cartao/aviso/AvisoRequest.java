package bootcamp.proposta.propostas.cartao.aviso;

import bootcamp.proposta.propostas.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoRequest {
    @JsonProperty
    @NotBlank
    private String destino;

    @JsonProperty
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate termino;

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    public void setTermino(LocalDate termino) {
        this.termino = termino;
    }

    public Aviso converte(Cartao cartao, String ip, String userAgent) {
        return new Aviso(cartao, destino, termino, ip, userAgent);
    }
}
