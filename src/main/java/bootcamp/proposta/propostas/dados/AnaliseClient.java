package bootcamp.proposta.propostas.dados;

import io.opentracing.contrib.spring.cloud.feign.FeignTracingAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "AnaliseClient", url="${academy.analise.url}",
        configuration = {FeignTracingAutoConfiguration.class })
public interface AnaliseClient {
    @PostMapping("/api/solicitacao")
    AnaliseResponse consulta(@RequestBody @Valid AnaliseRequest analiseRequest);
}
