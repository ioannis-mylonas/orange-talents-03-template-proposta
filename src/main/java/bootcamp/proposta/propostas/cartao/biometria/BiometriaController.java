package bootcamp.proposta.propostas.cartao.biometria;

import bootcamp.proposta.exceptions.NotFoundApiError;
import bootcamp.proposta.propostas.cartao.Cartao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/cartoes")
public class BiometriaController {
    private final EntityManager entityManager;

    public BiometriaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{cartaoId}/biometrias")
    @Transactional
    public ResponseEntity<?> cadastra(@PathVariable(name = "cartaoId") String cartaoId,
                                      @RequestBody @Valid BiometriaRequest request,
                                      UriComponentsBuilder uriBuilder) {

        Cartao cartao = entityManager.find(Cartao.class, cartaoId);
        if (cartao == null) throw new NotFoundApiError();

        Biometria biometria = request.converte();
        entityManager.persist(biometria);
        cartao.addBiometria(biometria);

        URI uri = uriBuilder
                .path("/api/cartoes/{cartaoId}/biometrias/{id}")
                .buildAndExpand(cartaoId, biometria.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{cartaoId}/biometrias/{id}")
    @Transactional
    public ResponseEntity<Biometria> detalhes(
            @PathVariable(name = "cartaoId") String cartaoId,
            @PathVariable(name = "id") Long id) {

        Cartao cartao = entityManager.find(Cartao.class, cartaoId);
        Biometria biometria = entityManager.find(Biometria.class, id);
        if (biometria == null || cartao == null)
            throw new NotFoundApiError();

        return ResponseEntity.ok(biometria);
    }
}
