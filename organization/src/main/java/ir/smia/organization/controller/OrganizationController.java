package ir.smia.organization.controller;

import ir.smia.organization.model.Organization;
import ir.smia.organization.service.OrganizationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/org")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{orgId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Organization> getOrganization(@PathVariable String orgId) {
        Organization organization = organizationService.getOrganization(orgId);

        return ResponseEntity.ok(organization);
    }
}
