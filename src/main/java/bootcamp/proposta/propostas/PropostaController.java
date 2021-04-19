package bootcamp.proposta.propostas;

import bootcamp.proposta.exceptions.ApiError;
import bootcamp.proposta.propostas.health.PropostaCounter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
    private final PropostaRepository propostaRepository;
    private final PropostaCounter propostaCounter;

    public PropostaController(PropostaRepository propostaRepository,
                              PropostaCounter propostaCounter) {

        this.propostaRepository = propostaRepository;
        this.propostaCounter = propostaCounter;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaRequest request,
                                      UriComponentsBuilder uriComponentsBuilder) {

        if (propostaRepository.existsByDocumento(request.getDocumento()))
            throw new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível processar o pedido.");

        Proposta proposta = request.converte();
        propostaRepository.save(proposta);
        propostaCounter.incrementProposta();

        URI uri = uriComponentsBuilder.path("/propostas/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalhes(@PathVariable(name = "id") Long id) {
        Optional<Proposta> proposta = propostaRepository.findById(id);
        if (proposta.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(proposta.get());
    }
}
