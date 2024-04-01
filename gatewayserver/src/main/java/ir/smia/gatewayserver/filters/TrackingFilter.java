package ir.smia.gatewayserver.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

            log.info("tmx-correlation-id generated in tracking filter: {}.", correlationId);
        }

        return chain.filter(exchange);
    }
}
