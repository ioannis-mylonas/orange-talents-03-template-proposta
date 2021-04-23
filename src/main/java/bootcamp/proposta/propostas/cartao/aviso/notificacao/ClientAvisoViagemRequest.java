package bootcamp.proposta.propostas.cartao.aviso.notificacao;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ClientAvisoViagemRequest {
    @NotBlank
    @JsonProperty
    private String destino;

    @NotNull
    @FutureOrPresent
    @JsonProperty
    private LocalDate validoAte;

    public ClientAvisoViagemRequest(@NotBlank String destino,
                                    @NotNull @FutureOrPresent LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }
}
