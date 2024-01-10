package ir.smia.demo.service;

import ir.smia.demo.dto.OrganizationDTO;
import ir.smia.demo.model.License;
import ir.smia.demo.service.client.OrganizationFeignClient;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LicenseService {

    private final OrganizationFeignClient organizationFeignClient;

    public LicenseService(OrganizationFeignClient organizationFeignClient) {
        this.organizationFeignClient = organizationFeignClient;
    }

    public License getLicense(String licenseId, String organizationId) {

        OrganizationDTO org = organizationFeignClient.getOrg(organizationId);

        License license = new License();
        license.setId(new Random().nextInt(1000));
        license.setLicenseId(licenseId);
        license.setOrganizationId(org.getId());
        license.setDescription("Software product" + org.getName());
        license.setProductName("Ostock");
        license.setLicenseType("full");
        return license;
    }

    public String createLicense(License license, String organizationId){
        String responseMessage = null;
        if(license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format("This is the post and the object is: %s", license.toString());
        }
        return responseMessage;
    }

    public String updateLicense(License license, String organizationId){
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format("This is the put and the object is: %s", license.toString());
        }
        return responseMessage;
    }
    public String deleteLicense(String licenseId, String organizationId){
        String responseMessage = null;
        responseMessage = String.format("Deleting license with id %s for the organization %s",licenseId, organizationId);
        return responseMessage;
    }
}
