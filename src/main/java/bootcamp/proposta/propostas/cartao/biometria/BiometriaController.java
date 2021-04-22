package bootcamp.proposta.propostas.cartao.biometria;

import bootcamp.proposta.propostas.PropostaRepository;
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
    private final PropostaRepository propostaRepository;
    private final EntityManager entityManager;

    public BiometriaController(PropostaRepository propostaRepository,
                               EntityManager entityManager) {

        this.propostaRepository = propostaRepository;
        this.entityManager = entityManager;
    }

    @PostMapping("/{cartaoId}/biometrias")
    @Transactional
    public ResponseEntity<?> cadastra(@PathVariable(name = "cartaoId") String cartaoId,
                                      @RequestBody @Valid BiometriaRequest request,
                                      UriComponentsBuilder uriBuilder) {

        if (!propostaRepository.existsByCartaoId(cartaoId))
            return ResponseEntity.notFound().build();

        Biometria biometria = request.converte(cartaoId);
        entityManager.persist(biometria);

        URI uri = uriBuilder
                .path("/api/cartoes/{cartaoId}/biometrias/{id}")
                .buildAndExpand(cartaoId, biometria.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{cartaoId}/biometrias/{id}")
    public ResponseEntity<Biometria> detalhes(
            @PathVariable(name = "cartaoId") String cartaoId,
            @PathVariable(name = "id") Long id) {

        if (!propostaRepository.existsByCartaoId(cartaoId))
            return ResponseEntity.notFound().build();

        Biometria biometria = entityManager.find(Biometria.class, id);
        if (biometria == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(biometria);
    }
}
