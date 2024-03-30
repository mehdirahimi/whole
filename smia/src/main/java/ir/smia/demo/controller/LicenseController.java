package ir.smia.demo.controller;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import ir.smia.demo.config.PropertiesConfig;
import ir.smia.demo.model.License;
import ir.smia.demo.service.LicenseService;
import ir.smia.demo.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/organization/{orgId}/license")
public class LicenseController {

    private final LicenseService licenseService;
    private final PropertiesConfig propertiesConfig;

    public LicenseController(LicenseService licenseService, PropertiesConfig propertiesConfig) {
        this.licenseService = licenseService;
        this.propertiesConfig = propertiesConfig;
    }

    @GetMapping(value = "/{licenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<License> getLicense(@PathVariable String orgId, @PathVariable String licenseId) {
        License license = licenseService.getLicense(licenseId, orgId);

        String test = propertiesConfig.getProperty();

        System.out.println(test);

        license.setLicenseId(test);

        log.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        return ResponseEntity.ok(license);
    }

    @GetMapping(value = "/circuit-breaker", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> testCircuitBreaker(@PathVariable String orgId) {
        System.out.println("org-id: " + orgId);

        try {
            licenseService.testCircuitBreaker();
        } catch (CallNotPermittedException exception) {
            System.out.println("CircuitBreaker is OPEN.");
        }

        return ResponseEntity.ok("OK");
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(@PathVariable String orgId, @RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request, orgId));
    }

    @PostMapping
    public ResponseEntity<String> createLicense(@PathVariable String orgId, @RequestBody License request) {
        return ResponseEntity.ok(licenseService.createLicense(request, orgId));
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable String orgId, @PathVariable String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, orgId));
    }
}
