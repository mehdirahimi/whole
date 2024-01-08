package ir.smia.demo.service.client;

import ir.smia.demo.dto.OrganizationDTO;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {
    private final DiscoveryClient discoveryClient;

    public OrganizationDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public OrganizationDTO getOrg(String orgId) {
        RestTemplate restTemplate = new RestTemplate();

        List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");

        if (instances.size() == 0) {
            return null;
        }

        String serviceUri = String.format("%/v1/org/%", instances.get(0).getUri().toString(), orgId);

        ResponseEntity<OrganizationDTO> restExchange = restTemplate.exchange(
                serviceUri, HttpMethod.GET, null, OrganizationDTO.class, orgId);

        return restExchange.getBody();
    }
}
