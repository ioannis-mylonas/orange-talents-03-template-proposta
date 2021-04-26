package bootcamp.proposta.config.tracing;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.springframework.stereotype.Component;

@Component
public class JaegerHeaderInterceptor implements RequestInterceptor {
    private final Tracer tracer;

    public JaegerHeaderInterceptor(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void apply(RequestTemplate template) {
        tracer.inject(tracer.activeSpan().context(),
                Format.Builtin.HTTP_HEADERS,
                new FeignTextMap(template));
    }
}
