package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.*;
import com.josgongon9.tfgwebbackend.model.response.OrganizationResponse;
import com.josgongon9.tfgwebbackend.repository.AlertRepository;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.repository.RoleRepository;
import com.josgongon9.tfgwebbackend.service.IOrganizationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrganizationServiceImpl extends BasicServiceImpl implements IOrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AlertRepository alertRepository;


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
        Optional<Organization> organizationBBDD = organizationRepository.findById(organization.getId());
        if (this.getUser().getRoles().stream().filter(role -> role.getName().equals(ERole.ROLE_ADMIN) ||  role.getName().equals(ERole.ROLE_MODERATOR)).findAny().isPresent()) {
                if (organizationBBDD.isPresent()) {
                    organizationRes = organizationRepository.save(organizationUpdate);
                } else {
                    throw new MyOwnException("Organización no encontrada");

                }


        } else {
            throw new MyOwnException("Usuario no permitido");
        }


        return organizationRes;
    }

    @Override
    public void deleteOrganization(String id) throws Exception {
        ObjectId idOrg = new ObjectId(id);
        List<User> modList = userRepository.findUserByOrganizations(idOrg);
        for (User mod : modList) {
            mod.getOrganizations().removeIf(x -> x.getId().equals(id));
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

    @Override
    public Organization getOrganizationByUser(String idUser) throws MyOwnException {
        ObjectId userId = new ObjectId(idUser);
        Organization organization = organizationRepository.findOrganizationByUsuarios(userId);


        return organization;
    }

    @Override
    public void updateMods(String id, String idUser) throws Exception {
        Organization organizationBBDD = organizationRepository.findById(id).orElseThrow(() -> new MyOwnException("Organizacion no encontrada"));
        User mod = userRepository.findById(idUser).orElseThrow(() -> new MyOwnException("Usuario no encontrado"));
        ObjectId idOrg = new ObjectId(id);
        List<User> listModByOrg = userRepository.findUserByOrganizations(idOrg);

        if (!listModByOrg.stream().anyMatch(x -> x.getId().equals(idUser))) {
            mod.getOrganizations().add(organizationBBDD);
        } else {
            mod.getOrganizations().removeIf(x -> x.getId().equals(id));
        }
        userRepository.save(mod);

    }

    @Override
    public List<Organization> getAll() {
        List<Organization> listOrg = new ArrayList<>();
        if (this.getRoles().contains(ERole.ROLE_ADMIN)) {
            listOrg = organizationRepository.findAll();
        } else if (this.getRoles().contains(ERole.ROLE_MODERATOR)) {
            listOrg = this.getUser().getOrganizations();
        }
        return listOrg;
    }

    @Override
    public List<Boolean> getUserOtherOrg(String idOrg) {
        List<Boolean> res = new ArrayList<>();
        ObjectId userId = new ObjectId();
        //Pertenece el usuario a alguna otra organizacion??

        ERole erole = ERole.valueOf(ERole.ROLE_USER.name());
        List<User> rolList = new ArrayList<User>();
        Role modRole = roleRepository.findByName(erole).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        ObjectId obj = new ObjectId(modRole.getId());

        rolList = userRepository.findAllByRole(obj);

        for (User user : rolList) {

            userId = new ObjectId(user.getId());
            Organization organization = organizationRepository.findOrganizationByUsuarios(userId);

            if (!Objects.isNull(organization) && !Objects.equals(organization.getId(), idOrg)) {
                res.add(true);
            } else {
                res.add(false);

            }
        }


        return res;
    }

    @Override
    public List<String> getOrganizationsByAlert() {
        List<String> res = new ArrayList<>();
        List<Alert> allAlert = alertRepository.findAll();
        for (Alert a : allAlert) {
            ObjectId obj = new ObjectId(a.getId());
            Organization org = organizationRepository.findOrganizationByAlert(obj);
            res.add(org.getName());
        }


        return res;
    }
}


