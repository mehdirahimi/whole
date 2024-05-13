package ir.smia.gatewayserver.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Order(1)
@Component
@Slf4j
@RequiredArgsConstructor
public class TrackingFilter implements GlobalFilter {

    private final FilterUtils filterUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();

        String correlationId = filterUtils.getCorrelationId(httpHeaders);

        if (correlationId != null) {
            log.info("tmx-correlation-id found in tracking filter: {}.", correlationId);
        } else {
            correlationId = filterUtils.generateCorrelationId();

            exchange = filterUtils.setCorrelationId(exchange, correlationId);

            log.info("tmx-correlation-id generated in tracking filter: {}", correlationId);
            log.info("preferred_username: {}", getUsername(httpHeaders));
        }

        return chain.filter(exchange);
    }

    public String getUsername(HttpHeaders httpHeaders) {
        String username = "";
        if (filterUtils.getAuthToken(httpHeaders) != null) {
            String jwtToken = filterUtils.getAuthToken(httpHeaders).replace("Bearer ", "");

            JSONObject jsonObject = null;

            try {
                jsonObject = decodeJWT(jwtToken);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            username = jsonObject.getString("preferred_username");
        }

        return username;
    }

    private JSONObject decodeJWT(String jwtToken) throws Exception {
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String[] chunks = jwtToken.split("\\.");

        if (chunks.length == 0) {
            throw new Exception("There is no JWT token to decode!");
        }

//        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        return new JSONObject(payload);
    }
}
