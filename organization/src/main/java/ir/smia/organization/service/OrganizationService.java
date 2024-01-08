package ir.smia.organization.service;

import ir.smia.organization.model.Organization;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OrganizationService {
    public Organization getOrganization(String organizationId){
        Organization organization = new Organization();
        organization.setId(new Random().nextInt(1000));
        organization.setName("Organization Name");
        organization.setDescription("Example Organization");
        return organization;
    }
}
