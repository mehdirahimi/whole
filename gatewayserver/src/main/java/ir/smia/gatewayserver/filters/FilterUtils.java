package ir.smia.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.UUID;

@Component
public class FilterUtils {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public String getCorrelationId(HttpHeaders httpHeaders) {
        List<String> headers = httpHeaders.get(CORRELATION_ID);

        if (headers == null) {
            return null;
        }

        return headers.get(0);
    }

    public String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return exchange
                .mutate()
                .request(exchange
                        .getRequest()
                        .mutate()
                        .header(CORRELATION_ID, correlationId)
                        .build())
                .build();
    }
}
