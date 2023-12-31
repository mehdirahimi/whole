package ir.smia.demo.controller;

import ir.smia.demo.model.License;
import ir.smia.demo.service.LicenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/organization/{orgId}/license")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable String orgId, @PathVariable String licenseId) {
        License license = licenseService.getLicense(licenseId, orgId);

        return ResponseEntity.ok(license);
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
