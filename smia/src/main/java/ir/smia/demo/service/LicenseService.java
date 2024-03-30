package ir.smia.demo.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ir.smia.demo.dto.OrganizationDTO;
import ir.smia.demo.model.License;
import ir.smia.demo.service.client.OrganizationFeignClient;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeoutException;

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

    @CircuitBreaker(name = "license-service-circuit-breaker", fallbackMethod = "fallbackRandomActivity")
    @Bulkhead(name = "license-service-bulkhead", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackRandomActivity")
    public void testCircuitBreaker() {
        System.out.println("inside testCircuitBreaker");
        randomlyRunLong();
    }

    private void randomlyRunLong() {
        System.out.println("inside randomlyRunLong");
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum==3) sleep();
    }

    private void sleep() {
        System.out.println("inside sleep");
        try {
            Thread.sleep(3000);
            throw new TimeoutException();
        } catch (Exception e) {
//            logger.error(e.getMessage());
            System.out.println("EXCEPTION THOEW");
        }
    }

    public void fallbackRandomActivity(Throwable throwable) {
        System.out.println("Watch a video from TechPrimers" + Runtime.getRuntime().availableProcessors());
    }

}
