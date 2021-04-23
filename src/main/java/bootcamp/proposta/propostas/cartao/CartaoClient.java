package bootcamp.proposta.propostas.cartao;

import bootcamp.proposta.propostas.cartao.aviso.notificacao.ClientAvisoViagemRequest;
import bootcamp.proposta.propostas.cartao.aviso.notificacao.ClientAvisoViagemResponse;
import bootcamp.proposta.propostas.cartao.bloqueio.ClienteBloqueioRequest;
import bootcamp.proposta.propostas.cartao.bloqueio.ClienteBloqueioResponse;
import bootcamp.proposta.propostas.cartao.request.CartaoServiceResponse;
import io.opentracing.contrib.spring.cloud.feign.FeignTracingAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(value = "CartaoClient", url = "${academy.cartoes.url}",
        configuration = {FeignTracingAutoConfiguration.class})
public interface CartaoClient {
    @GetMapping("/api/cartoes")
    CartaoServiceResponse consulta(@RequestParam(name = "idProposta") Long idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    ClienteBloqueioResponse bloqueia(@PathVariable(name = "id") String id,
                                     @RequestBody ClienteBloqueioRequest request);

    @PostMapping("/api/cartoes/{id}/avisos")
    ClientAvisoViagemResponse avisaViagem(@PathVariable(name = "id") String id,
                                          @RequestBody @Valid ClientAvisoViagemRequest request);
}
