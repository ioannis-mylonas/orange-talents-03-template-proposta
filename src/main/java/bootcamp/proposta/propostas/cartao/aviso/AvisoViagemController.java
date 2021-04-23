package bootcamp.proposta.propostas.cartao.aviso;

import bootcamp.proposta.propostas.cartao.Cartao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class AvisoViagemController {
    private final EntityManager entityManager;

    public AvisoViagemController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/api/cartoes/{id}/avisos")
    @Transactional
    public ResponseEntity<?> avisa(@PathVariable(name = "id") String id,
                                   @RequestBody @Valid AvisoViagemRequest viagemRequest,
                                   @RequestHeader("User-Agent") String userAgent,
                                   HttpServletRequest request) {

        Cartao cartao = entityManager.find(Cartao.class, id);
        if (cartao == null)
            return ResponseEntity.notFound().build();

        String ip = request.getRemoteAddr();
        AvisoViagem aviso = viagemRequest.converte(cartao, ip, userAgent);
        cartao.addAvisoViagem(aviso);

        entityManager.merge(cartao);
        return ResponseEntity.ok().build();
    }
}
