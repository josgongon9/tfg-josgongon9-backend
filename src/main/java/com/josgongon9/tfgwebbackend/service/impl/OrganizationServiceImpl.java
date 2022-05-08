package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.ERole;
import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.response.OrganizationResponse;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.service.IOrganizationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl extends BasicServiceImpl implements IOrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public Organization createOrganization(OrganizationResponse organization) throws Exception {
        Organization organizationRes;
        List<String> modListUser = organization.getModerador();
        List<User> modList = userRepository.findAllByUsernameIn(modListUser);
        if (this.getUser().getRoles().stream().filter(role -> role.getName().equals(ERole.ROLE_ADMIN)).findAny().isPresent()) {
            if (!modList.isEmpty() && modList.stream().allMatch(user -> user.getRoles().stream().allMatch(role -> role.getName().equals(ERole.ROLE_MODERATOR)))) {
                if (!organizationRepository.findFirstByName(organization.getName()).isPresent()) {
                    organizationRes = organizationRepository.save(new Organization(organization.getId(), organization.getName(), organization.getDescription(), organization.getPhoneNumber(), organization.getUrl(), organization.getCountry(), organization.getProvince(), organization.getCity(), new ArrayList<>()));
                    for (User mod : modList) {
                        mod.getOrganizations().add(organizationRes);
                        userRepository.save(mod);
                    }

                } else {
                    throw new MyOwnException("Organización ya existente");

                }
            } else {
                throw new MyOwnException("Moderadores no permitido");
            }

        } else {
            throw new MyOwnException("Usuario no permitido");
        }


        return organizationRes;
    }

    @Override
    public Organization updateOrganization(OrganizationResponse organization) throws Exception {
        Organization organizationRes;
        Organization organizationUpdate = new Organization(organization);
        List<String> modListUser = organization.getModerador();
        List<User> modList = userRepository.findAllByUsernameIn(modListUser);
        Optional<Organization> organizationBBDD = organizationRepository.findById(organization.getId());
        if (this.getUser().getRoles().stream().filter(role -> role.getName().equals(ERole.ROLE_ADMIN)).findAny().isPresent()) {
            if (!modList.isEmpty() && modList.stream().allMatch(user -> user.getRoles().stream().allMatch(role -> role.getName().equals(ERole.ROLE_MODERATOR)))) {
                if (organizationBBDD.isPresent()) {
                    organizationRes = organizationRepository.save(organizationUpdate);
                } else {
                    throw new MyOwnException("Organización no encontrada");

                }

            } else {
                throw new MyOwnException("Moderadores no permitido");
            }

        } else {
            throw new MyOwnException("Usuario no permitido");
        }


        return organizationRes;
    }

    @Override
    public void deleteOrganization(String id) throws Exception {
        ObjectId idOrg = new ObjectId(id);
        List<User> modList = userRepository.findModeradoresByOrganization(idOrg);
        for (User mod : modList) {
            mod.getOrganizations().remove(id);
            userRepository.save(mod);
        }
        organizationRepository.deleteById(id);
    }

    @Override
    public Organization updateUsers(String id, String idUser) throws Exception {
        Organization organizationRes;
        Organization organizationBBDD = organizationRepository.findById(id).orElseThrow(() -> new MyOwnException("Organizacion no encontrada"));
        User user = userRepository.findById(idUser).orElseThrow(() -> new MyOwnException("Usuario no encontrado"));
        Boolean res = organizationBBDD.getUsuarios().stream().anyMatch(x -> x.getId().equals(idUser));
        if (!res) {
            organizationBBDD.getUsuarios().add(user);
        } else {
            organizationBBDD.getUsuarios().removeIf(x -> x.getId().equals(idUser));
        }
        organizationRes = organizationRepository.save(organizationBBDD);


        return organizationRes;
    }
}



