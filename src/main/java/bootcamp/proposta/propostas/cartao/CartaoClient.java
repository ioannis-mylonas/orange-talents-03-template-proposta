package bootcamp.proposta.propostas.cartao;

import bootcamp.proposta.propostas.cartao.request.CartaoResponse;
import io.opentracing.contrib.spring.cloud.feign.FeignTracingAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "CartaoClient", url = "${academy.cartoes.url}",
        configuration = {FeignTracingAutoConfiguration.class})
public interface CartaoClient {
    @GetMapping("/api/cartoes")
    public CartaoResponse consulta(@RequestParam(name = "idProposta") Long idProposta);
}
