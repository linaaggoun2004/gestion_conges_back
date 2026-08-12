package com.example.gestion_conges_back.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.EmployeListDto;
import com.example.gestion_conges_back.DTO.IndicateurResponse;
import com.example.gestion_conges_back.DTO.ProfilResponse;
import com.example.gestion_conges_back.DTO.RolseResponse;
import com.example.gestion_conges_back.DTO.Soldedto;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.entity.SoldeConge;
import com.example.gestion_conges_back.entity.StatutEnum;
import com.example.gestion_conges_back.repository.Demanderepository;
import com.example.gestion_conges_back.repository.Employerepository;
import com.example.gestion_conges_back.repository.SoldeCongerepository;

@Service
public class EmpService {

    private Employerepository empRep;
    private Demanderepository demandeRep;
    private  SoldeCongerepository soldeRep;

    public EmpService(Demanderepository demandeRep, Employerepository empRep,SoldeCongerepository soldeRep) {
        this.demandeRep = demandeRep;
        this.empRep = empRep;
        this.soldeRep=soldeRep;
    }

    public RolseResponse changerRole(Long id, RoleEnum role) {
        Employe employe = empRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        employe.setRole(role);
        empRep.save(employe);
        return new RolseResponse(employe.getIdE(),employe.getNom(),employe.getPrenom(),employe.getService().getNom(),employe.getPoste(),employe.getRole());
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

    public IndicateurResponse indicateursManager(Long id){
        Long totalEmp=empRep.countByManager_IdE(id);
        Long totalEnAttentes=demandeRep.countByStatut(StatutEnum.EN_ATTENTE_MANAGER);
        return new IndicateurResponse(totalEmp,totalEnAttentes);
    }

    public List<EmployeListDto> gatAllEmployesAvecDetails(){
        int anneeCourante=LocalDate.now().getYear();
        List<Employe> emps=empRep.findAll();
        List<EmployeListDto> resultats = new ArrayList<>();

        for(Employe e:emps){
            SoldeConge solde = soldeRep
                .findByEmployeIdEAndAnnee(e.getIdE(), anneeCourante)
                .orElse(null);
            EmployeListDto r=new EmployeListDto();
            r.setIdE(e.getIdE());
            r.setNom(e.getNom());
            r.setPrenom(e.getPrenom());
            r.setEmail(e.getUsername());
            r.setPoste(e.getPoste());
            r.setRole(e.getRole());

            if (e.getService() != null) {
            r.setServiceId(e.getService().getIdS());
            r.setServiceNom(e.getService().getNom());
            }

            if(e.getManager()!=null ){
                r.setManagerId(e.getManager().getIdE());
                r.setManagerNom(
                    e.getManager().getPrenom() + " " + e.getManager().getNom()
                );
            }

            if (solde != null) {
            r.setSoldeTotal(solde.getTotalJours());
            r.setSoldeUtilise(solde.getJoursUtilises());
            r.setSoldeRestant(solde.getJoursRestantes());
            } else {
            r.setSoldeTotal(0.0);
            r.setSoldeUtilise(0.0);
            r.setSoldeRestant(0.0);
        }
        resultats.add(r);
        }
        return resultats;
    }

    @Transactional
    public SoldeConge modifierSolde(Long employeId, Double nouveauTotal) {
        int annee = LocalDate.now().getYear();
        Employe employe = empRep.findById(employeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé introuvable"));

        SoldeConge solde = soldeRep.findByEmployeIdEAndAnnee(employeId, annee)
                .orElseGet(() -> {
                    SoldeConge s = new SoldeConge();
                    s.setEmploye(employe);
                    s.setAnnee(annee);
                    s.setJoursUtilises(0.0);
                    return s;
                });

        solde.setTotalJours(nouveauTotal);
        solde.setJoursRestantes(nouveauTotal - solde.getJoursUtilises());
        return soldeRep.save(solde);
    }

    @Transactional
    public void reinitialiserTousLesSoldes(Double nouvelleValeur) {
        int annee = LocalDate.now().getYear();
        List<Employe> employes = empRep.findAll();

        for (Employe e : employes) {
            SoldeConge solde = soldeRep.findByEmployeIdEAndAnnee(e.getIdE(), annee)
                    .orElseGet(() -> {
                        SoldeConge s = new SoldeConge();
                        s.setEmploye(e);
                        s.setAnnee(annee);
                        return s;
                    });
            solde.setTotalJours(nouvelleValeur);
            solde.setJoursUtilises(0.0);
            solde.setJoursRestantes(nouvelleValeur);
            soldeRep.save(solde);
        }
    }

    public void reinitialisationAutomatiqueMensuelle() {
        reinitialiserTousLesSoldes(90.0); 
    }

    public Soldedto getSolde(Long id){
        int annee=LocalDate.now().getYear();
        SoldeConge solde=soldeRep.findByEmployeIdEAndAnnee(id,annee)
        .orElseThrow(() ->
                new RuntimeException("Solde introuvable pour cet employé")
            );
        return new Soldedto(solde.getTotalJours(),solde.getJoursUtilises(),solde.getJoursRestantes());
    }





}
