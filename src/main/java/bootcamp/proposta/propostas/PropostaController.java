package bootcamp.proposta.propostas;

import bootcamp.proposta.exceptions.ApiError;
import bootcamp.proposta.propostas.events.PropostaEventPublisher;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {
    private final PropostaRepository propostaRepository;
    private final PropostaEventPublisher eventPublisher;
    private final Tracer tracer;

    public PropostaController(PropostaRepository propostaRepository,
                              PropostaEventPublisher eventPublisher,
                              Tracer tracer) {

        this.propostaRepository = propostaRepository;
        this.eventPublisher = eventPublisher;
        this.tracer = tracer;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaRequest request,
                                      UriComponentsBuilder uriComponentsBuilder) {
        Span span = tracer.activeSpan();
        span.log("Cadastrando proposta...");

        if (propostaRepository.existsByDocumento(request.getDocumento()))
            throw new ApiError(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Não foi possível processar o pedido.");

        Proposta proposta = request.converte();
        proposta = propostaRepository.save(proposta);
        eventPublisher.propostaCriada(proposta);

        URI uri = uriComponentsBuilder.path("/propostas/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();

        span.log("Cadastro de proposta finalizado.");
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> detalhes(@PathVariable(name = "id") Long id) {
        Optional<Proposta> proposta = propostaRepository.findById(id);
        if (proposta.isEmpty()) return ResponseEntity.notFound().build();

        PropostaResponse response = new PropostaResponse(proposta.get());
        return ResponseEntity.ok(response);
    }
}
