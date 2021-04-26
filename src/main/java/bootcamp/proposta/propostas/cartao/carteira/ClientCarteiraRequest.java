package bootcamp.proposta.propostas.cartao.carteira;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClientCarteiraRequest {
    @NotNull
    @JsonProperty
    private CarteiraEnum carteira;

    @Email
    @NotBlank
    @JsonProperty
    private String email;

    public ClientCarteiraRequest(@NotNull CarteiraEnum carteira, @Email @NotBlank String email) {
        this.carteira = carteira;
        this.email = email;
    }

    public CarteiraEnum getCarteira() {
        return carteira;
    }
}
