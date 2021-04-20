package bootcamp.proposta.propostas.cartao.biometria;

import bootcamp.proposta.validators.IsBase64;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {
    @JsonProperty
    @NotBlank
    @IsBase64
    private final byte[] biometria;

    public BiometriaRequest(@NotBlank byte[] biometria) {
        this.biometria = biometria;
    }

    public Biometria converte(String idCartao) {
        return new Biometria(idCartao, biometria);
    }
}
