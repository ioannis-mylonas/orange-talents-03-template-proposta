package bootcamp.proposta.propostas.cartao.aviso;

import bootcamp.proposta.propostas.cartao.Cartao;
import bootcamp.proposta.propostas.cartao.CartaoClient;
import bootcamp.proposta.propostas.cartao.aviso.notificacao.ClientAvisoViagemRequest;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

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
                                   HttpServletRequest request) {

        Cartao cartao = entityManager.find(Cartao.class, id);
        if (cartao == null)
            return ResponseEntity.notFound().build();

        String ip = request.getRemoteAddr();
        Aviso aviso = viagemRequest.converte(cartao, ip, userAgent);

        try {
            avisaViagem(aviso, cartao);
            return ResponseEntity.ok().build();
        } catch (FeignException.UnprocessableEntity ex) {
            ex.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        } catch (FeignException.FeignClientException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void avisaViagem(Aviso aviso, Cartao cartao) throws FeignException.FeignClientException {
        client.avisaViagem(cartao.getId(),
                new ClientAvisoViagemRequest(aviso.getDestino(), aviso.getTermino()));

        cartao.addAviso(aviso);
        entityManager.merge(cartao);
    }
}
