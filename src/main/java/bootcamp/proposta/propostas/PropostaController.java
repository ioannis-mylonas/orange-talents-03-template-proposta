package bootcamp.proposta.propostas;

import bootcamp.proposta.propostas.health.PropostaCounter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
    private final EntityManager entityManager;
    private final PropostaCounter propostaCounter;

    public PropostaController(EntityManager entityManager, PropostaCounter propostaCounter) {
        this.entityManager = entityManager;
        this.propostaCounter = propostaCounter;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaRequest request,
                                      UriComponentsBuilder uriComponentsBuilder) {
        Proposta proposta = request.converte();
        entityManager.persist(proposta);
        propostaCounter.incrementProposta();

        URI uri = uriComponentsBuilder.path("/propostas/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalhes(@PathVariable(name = "id") Long id) {
        Proposta proposta = entityManager.find(Proposta.class, id);
        if (proposta == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(proposta);
    }
}
