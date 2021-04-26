package bootcamp.proposta.propostas.cartao.carteira;

import bootcamp.proposta.propostas.cartao.Cartao;
import bootcamp.proposta.propostas.cartao.CartaoClient;
import bootcamp.proposta.propostas.cartao.CartaoRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class AssociacaoCarteiraController {
    private final AssociacaoCarteiraRepository repository;
    private final CartaoRepository cartaoRepository;
    private final CartaoClient client;

    public AssociacaoCarteiraController(AssociacaoCarteiraRepository repository,
                                        CartaoRepository cartaoRepository,
                                        CartaoClient client) {
        this.repository = repository;
        this.cartaoRepository = cartaoRepository;
        this.client = client;
    }

    @PostMapping("/api/cartoes/{id}/carteiras")
    @Transactional
    public ResponseEntity<?> associa(@PathVariable(name = "id") String id,
                                     @RequestBody @Valid ClientCarteiraRequest request,
                                     UriComponentsBuilder uriBuilder) {

        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isEmpty())
            return ResponseEntity.notFound().build();

        if (repository.existsByCartaoAndCarteira(cartao.get(), request.getCarteira()))
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        try {
            ClientCarteiraResponse response = client.associa(id, request);
            return processa(response, request, cartao.get(), uriBuilder);
        } catch (FeignException.FeignClientException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<?> processa(ClientCarteiraResponse response,
                                     ClientCarteiraRequest request,
                                      Cartao cartao,
                                      UriComponentsBuilder uriBuilder) {

        AssociacaoCarteira associacao = response.converte(request.getCarteira(), cartao);
        cartao.addAssociacaoCarteira(associacao);
        cartaoRepository.save(cartao);

        URI uri = uriBuilder
                .path("/api/cartoes/{id}/carteiras/{idCarteira}")
                .buildAndExpand(cartao.getId(), associacao.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
