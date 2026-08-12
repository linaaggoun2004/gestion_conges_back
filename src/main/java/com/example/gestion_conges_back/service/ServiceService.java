package com.example.gestion_conges_back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.ServiceCreateRequest;
import com.example.gestion_conges_back.DTO.Servicedto;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.repository.Employerepository;
import com.example.gestion_conges_back.repository.Servicerepository;

@Service
public class ServiceService {
    private  Servicerepository servicerep;
    private  Employerepository emprep;
    public ServiceService(Servicerepository servicerep, Employerepository emprep) {
        this.servicerep = servicerep;
        this.emprep = emprep;
    }
    public List<Servicedto> getAllServiceAvecStats(){
        List<com.example.gestion_conges_back.entity.Service> services=servicerep.findAll();
        List <Servicedto> resultats=new ArrayList<>();
        for (com.example.gestion_conges_back.entity.Service s : services) {

        Servicedto dto = new Servicedto(
                s.getIdS(),
                s.getNom(),
                s.getDescription()
        );

        dto.setNbEmployes(
                (int) emprep.countByServiceIdS(s.getIdS())
        );
        if (s.getManager() != null) {

            dto.setManagerId(s.getManager().getIdE());

            dto.setManagerNom(
                    s.getManager().getNom()
            );

            dto.setManagerPrenom(
                    s.getManager().getPrenom()
            );
        }
        resultats.add(dto);
    }

    return resultats;

    }

    public com.example.gestion_conges_back.entity.Service creerService(ServiceCreateRequest request) {
        com.example.gestion_conges_back.entity.Service s = new com.example.gestion_conges_back.entity.Service();
        s.setNom(request.getNom());
        s.setDescription(request.getDescription());
        return servicerep.save(s);
    }

    public com.example.gestion_conges_back.entity.Service assignerManager(Long serviceId, Long managerId) {
        com.example.gestion_conges_back.entity.Service s = servicerep.findById(serviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service introuvable"));
        Employe manager = emprep.findById(managerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé introuvable"));

        if (manager.getRole() != RoleEnum.MANAGER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'employé désigné doit avoir le rôle MANAGER");
        }
        s.setManager(manager);
        return servicerep.save(s);
    }


}
