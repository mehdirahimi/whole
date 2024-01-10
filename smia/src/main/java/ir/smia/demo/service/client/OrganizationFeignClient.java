package ir.smia.demo.service.client;

import ir.smia.demo.dto.OrganizationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("organization-service")
public interface OrganizationFeignClient {
    @GetMapping(value = "/v1/org/{orgId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    OrganizationDTO getOrg(@PathVariable String orgId);
}
