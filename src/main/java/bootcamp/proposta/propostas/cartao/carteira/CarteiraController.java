package bootcamp.proposta.propostas.cartao.carteira;

import bootcamp.proposta.exceptions.ApiError;
import bootcamp.proposta.exceptions.InternalApiError;
import bootcamp.proposta.exceptions.NotFoundApiError;
import bootcamp.proposta.exceptions.UnprocessableApiError;
import bootcamp.proposta.propostas.cartao.Cartao;
import bootcamp.proposta.propostas.cartao.CartaoClient;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class CarteiraController {
    private final CarteiraRepository repository;
    private final EntityManager entityManager;
    private final CartaoClient client;

    public CarteiraController(CarteiraRepository repository,
                              EntityManager entityManager,
                              CartaoClient client) {
        this.repository = repository;
        this.entityManager = entityManager;
        this.client = client;
    }

    @PostMapping("/api/cartoes/{id}/carteiras")
    @Transactional
    public ResponseEntity<?> associa(@PathVariable(name = "id") String id,
                                     @RequestBody @Valid ClientCarteiraRequest request,
                                     UriComponentsBuilder uriBuilder) {

        Cartao cartao = entityManager.find(Cartao.class, id);
        if (cartao == null) throw new NotFoundApiError();

        if (repository.existsByCartaoAndCarteira(cartao, request.getCarteira()))
            throw new UnprocessableApiError();

        return processa(request, cartao, uriBuilder);
    }

    public ResponseEntity<?> processa(ClientCarteiraRequest request,
                                      Cartao cartao,
                                      UriComponentsBuilder uriBuilder) throws ApiError {
        try {
            ClientCarteiraResponse response = client.associa(cartao.getId(), request);

            Carteira carteira = response.converte(request.getCarteira(), cartao);
            cartao.addCarteira(carteira);

            entityManager.persist(carteira);

            URI uri = uriBuilder
                    .path("/api/cartoes/{id}/carteiras/{idCarteira}")
                    .buildAndExpand(cartao.getId(), carteira.getId())
                    .toUri();

            return ResponseEntity.created(uri).build();
        } catch (FeignException.FeignClientException.UnprocessableEntity ex) {
            ex.printStackTrace();
            throw new UnprocessableApiError();
        } catch (FeignException.FeignClientException ex) {
            ex.printStackTrace();
            throw new InternalApiError();
        }
    }
}
