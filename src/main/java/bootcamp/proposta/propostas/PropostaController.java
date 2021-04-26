package bootcamp.proposta.propostas;

import bootcamp.proposta.exceptions.NotFoundApiError;
import bootcamp.proposta.exceptions.UnprocessableApiError;
import bootcamp.proposta.propostas.events.PropostaEventPublisher;
import io.opentracing.Span;
import io.opentracing.Tracer;
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
    private final Tracer tracer;
    private final PropostaEventPublisher eventPublisher;

    public PropostaController(PropostaRepository propostaRepository,
                              Tracer tracer, PropostaEventPublisher eventPublisher) {

        this.propostaRepository = propostaRepository;
        this.tracer = tracer;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaRequest request,
                                      UriComponentsBuilder uriComponentsBuilder) {
        Span span = tracer.activeSpan();
        span.log("Cadastrando proposta...");

        if (propostaRepository.existsByDocumento(request.getDocumento()))
            throw new UnprocessableApiError();

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
        if (proposta.isEmpty()) throw new NotFoundApiError();

        PropostaResponse response = new PropostaResponse(proposta.get());
        return ResponseEntity.ok(response);
    }
}
