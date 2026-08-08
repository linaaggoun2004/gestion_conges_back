package com.example.gestion_conges_back.service;

import org.springframework.stereotype.Service;

import com.example.gestion_conges_back.DTO.IndicateurResponse;
import com.example.gestion_conges_back.DTO.ProfilResponse;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.entity.StatutEnum;
import com.example.gestion_conges_back.repository.Demanderepository;
import com.example.gestion_conges_back.repository.Employerepository;

@Service
public class EmpService {

    private Employerepository empRep;
    private Demanderepository demandeRep;

    public EmpService(Demanderepository demandeRep, Employerepository empRep) {
        this.demandeRep = demandeRep;
        this.empRep = empRep;
    }

    public Employe changerRole(Long id, RoleEnum role) {
        Employe employe = empRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        employe.setRole(role);
        return empRep.save(employe);
    }

    public Employe changerManager(Long employeId, Long managerId) {

        Employe employe = empRep.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        Employe manager = empRep.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager introuvable"));

        if (manager.getRole() != RoleEnum.MANAGER && manager.getRole() != RoleEnum.RH) {
            throw new RuntimeException("Cet employé n'est pas manager.");
        }

        employe.setManager(manager);

        return empRep.save(employe);
    }

    public Employe getEmploye(Long employeId) {
        return empRep.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));
    }

    public ProfilResponse getEmploye2(Long employeId) {
        Employe emp = empRep.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        String nomService = null;
        if (emp.getService() != null) {
            nomService = emp.getService().getNom();
        }
        String nomManager = null;
        if (emp.getManager() != null) {
            nomManager = emp.getManager().getNom()
                    + " "
                    + emp.getManager().getPrenom();
        }else{
            nomManager="aucun manager pour le moment";
        }
        return new ProfilResponse(
                emp.getAdresse(),
                emp.getDateEntree().toString(),
                emp.getDateNaissance(),
                emp.getUsername(),
                nomManager,
                emp.getNom(),
                emp.getPoste(),
                emp.getPrenom(),
                nomService,
                emp.getSituationFamiliale(),
                emp.getTelephone());
    }

    public IndicateurResponse indicateurs(){
        Long totalEmp=empRep.count();
        Long totalEnAttentes=demandeRep.countByStatut(StatutEnum.EN_ATTENTE_RH);
        return new IndicateurResponse(totalEmp,totalEnAttentes);

    }

}
