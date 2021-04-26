package bootcamp.proposta.config.tracing;

import feign.RequestTemplate;
import io.opentracing.propagation.TextMap;

import java.util.Iterator;
import java.util.Map;

public class FeignTextMap implements TextMap {

    private final RequestTemplate request;

    public FeignTextMap(RequestTemplate request) {
        this.request = request;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return null;
    }

    @Override
    public void put(String key, String value) {
        this.request.header(key, value);
    }
}
