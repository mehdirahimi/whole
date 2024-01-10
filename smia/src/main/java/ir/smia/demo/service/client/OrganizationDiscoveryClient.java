package ir.smia.demo.service.client;

import ir.smia.demo.dto.OrganizationDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationDiscoveryClient {
    private final RestTemplate restTemplate;

    public OrganizationDiscoveryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrganizationDTO getOrg(String orgId) {

        String serviceUri = "http://organization-service/v1/org/{orgId}";

        ResponseEntity<OrganizationDTO> restExchange = restTemplate.exchange(
                serviceUri, HttpMethod.GET, null, OrganizationDTO.class, orgId);

        return restExchange.getBody();
    }
}
