package bootcamp.proposta.propostas.cartao.aviso;

import bootcamp.proposta.exceptions.ApiError;
import bootcamp.proposta.propostas.cartao.Cartao;
import bootcamp.proposta.propostas.cartao.CartaoClient;
import bootcamp.proposta.propostas.cartao.aviso.notificacao.ClientAvisoViagemRequest;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class AvisoController {
    private final EntityManager entityManager;
    private final CartaoClient client;

    public AvisoController(EntityManager entityManager,
                           CartaoClient client) {

        this.entityManager = entityManager;
        this.client = client;
    }

    @PostMapping("/api/cartoes/{id}/avisos")
    @Transactional
    public ResponseEntity<?> avisa(@PathVariable(name = "id") String id,
                                   @RequestBody @Valid AvisoRequest viagemRequest,
                                   @RequestHeader("User-Agent") String userAgent,
                                   HttpServletRequest request, UriComponentsBuilder uriBuilder) {

        Cartao cartao = entityManager.find(Cartao.class, id);
        if (cartao == null)
            return ResponseEntity.notFound().build();

        String ip = request.getRemoteAddr();
        Aviso aviso = viagemRequest.converte(cartao, ip, userAgent);

        try {
            URI uri = avisaViagem(aviso, cartao, uriBuilder);
            return ResponseEntity.created(uri).build();
        } catch (FeignException.UnprocessableEntity ex) {
            ex.printStackTrace();
            throw new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível processar o pedido.");
        } catch (FeignException.FeignClientException ex) {
            ex.printStackTrace();
            throw new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento do pedido, por favor tente novamente mais tarde.");
        }
    }

    private URI avisaViagem(Aviso aviso, Cartao cartao, UriComponentsBuilder uriBuilder) throws FeignException.FeignClientException {
        client.avisaViagem(cartao.getId(),
                new ClientAvisoViagemRequest(aviso.getDestino(), aviso.getTermino()));

        cartao.addAviso(aviso);
        entityManager.persist(aviso);

        return uriBuilder
                .path("/api/cartoes/{id}/avisos/{idAviso}")
                .buildAndExpand(cartao.getId(), aviso.getId())
                .toUri();
    }
}
