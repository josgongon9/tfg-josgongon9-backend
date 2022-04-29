package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.model.ERole;
import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.response.OrganizationResponse;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl extends BasicServiceImpl implements IOrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Organization createOrganization(OrganizationResponse organization) throws Exception {
        Organization organizationRes;
            List<String> modListUser = organization.getModerador();
            List<User> modList = userRepository.findAllByUsernameIn(modListUser);
        if (this.getUser().getRoles().stream().filter(role -> role.getName().equals(ERole.ROLE_ADMIN)).findAny().isPresent()) {
            if (modList.stream().allMatch(user -> user.getRoles().stream().allMatch(role -> role.getName().equals(ERole.ROLE_MODERATOR)))) {
                organizationRes = organizationRepository.save(new Organization(organization.getId(), organization.getName(), organization.getDescription(), organization.getPhoneNumber(), organization.getUrl(), organization.getCountry(), organization.getProvince(), organization.getCity()/* organization.getModerador()*/));
            } else {
                throw new Exception("Moderadores no permitido");
            }

        } else {
            throw new Exception("Usuario no permitido");
        }


        return organizationRes;
    }


}
