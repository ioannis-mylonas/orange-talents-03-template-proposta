package bootcamp.proposta.propostas.cartao.bloqueio;

import bootcamp.proposta.propostas.cartao.Cartao;
import bootcamp.proposta.propostas.cartao.CartaoClient;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
public class BloqueioController {
    private final EntityManager entityManager;
    private final CartaoClient client;

    public BloqueioController(EntityManager entityManager, CartaoClient client) {
        this.entityManager = entityManager;
        this.client = client;
    }

    @PostMapping("/api/cartoes/{id}/bloqueios")
    @Transactional
    public ResponseEntity<?> bloqueia(@PathVariable(name = "id") String cartaoId,
                                      @RequestHeader("User-Agent") String userAgent,
                                      HttpServletRequest request) {

        Cartao cartao = entityManager.find(Cartao.class, cartaoId);
        if (cartao == null)
            return ResponseEntity.notFound().build();

        if (!cartao.getBloqueios().isEmpty())
            return ResponseEntity.unprocessableEntity().build();

        return processa(cartao, request, userAgent);
    }

    private ResponseEntity<?> processa(Cartao cartao, HttpServletRequest request, String userAgent) {
        try {
            ClientBloqueioResponse response = client.bloqueia(cartao.getId(), new ClientBloqueioRequest());

            Bloqueio bloqueio = new Bloqueio(cartao, request.getRemoteAddr(), userAgent);
            cartao.bloqueia(bloqueio);
            cartao.mudaEstado(response.getResultado());

            entityManager.merge(cartao);
            return ResponseEntity.ok().build();
        } catch(FeignException.UnprocessableEntity ex) {
            ex.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        } catch (FeignException.FeignClientException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
