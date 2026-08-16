package com.example.gestion_conges_back.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.entity.Demande;
import com.example.gestion_conges_back.entity.DemandeConge;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.entity.StatutEnum;
import com.example.gestion_conges_back.repository.Employerepository;

@Component
public class WorkflowService {

    private  NotificationService notificationService;

    private Employerepository employeRep;

    private EmpService empSer;
    

    public WorkflowService(NotificationService notificationService, Employerepository employeRep, EmpService empSer) {
        this.notificationService = notificationService;
        this.employeRep = employeRep;
        this.empSer = empSer;
    }

    public boolean estManagerDe(Employe manager, Employe employe) {
        if (employe.getManager() != null && employe.getManager().getIdE().equals(manager.getIdE())) {
            return true;
        }
        if (employe.getService() != null
                && employe.getService().getManager() != null
                && employe.getService().getManager().getIdE().equals(manager.getIdE())) {
            return true;
        }
        return false;
    }

    public Employe trouverManagerDe(Employe employe) {
        
        if (employe.getManager() != null) return employe.getManager();
        if (employe.getService() != null) return employe.getService().getManager();
        return null;
    }

    public void validerParManager(Demande demande, Employe manager) {
        if (!estManagerDe(manager, demande.getEmploye())) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas le manager de cet employé");
    }
        if (demande.getStatut() != StatutEnum.EN_ATTENTE_MANAGER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cette demande n'est pas en attente de validation manager");
        }
        demande.setStatut(StatutEnum.VALIDEE_MANAGER);
        notificationService.creerNotification(demande.getEmploye(),
                "Votre demande du " + demande.getDateDebut() + " au " + demande.getDateFin()
                        + " a été validée par votre manager et est en attente de validation RH.");

        Employe rh = employeRep.findFirstByRole(RoleEnum.RH)
            .orElseThrow(() ->
                    new RuntimeException("Aucun RH trouvé")
            );

        // 4. Notification du RH
        notificationService.creerNotification(
                rh,
                "Une nouvelle demande de "
                        + demande.getEmploye().getPrenom()
                        + " "
                        + demande.getEmploye().getNom()
                        + " est en attente de votre validation."
        );
        }

    public void refuserParManager(Demande demande, Employe manager) {
        if (!estManagerDe(manager, demande.getEmploye())) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas le manager de cet employé");
        }
        if (demande.getStatut() != StatutEnum.EN_ATTENTE_MANAGER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cette demande n'est pas en attente de validation manager");
        }
        demande.setStatut(StatutEnum.REFUSEE);
        notificationService.creerNotification(demande.getEmploye(),
                "Votre demande du " + demande.getDateDebut() + " au " + demande.getDateFin()
                        + " a été refusée par votre manager.");
    }

    public void validerParRH(Demande demande) {
        if (demande.getStatut() != StatutEnum.VALIDEE_MANAGER) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cette demande n'est pas en attente de validation RH");
        }
        Employe employe = demande.getEmploye();

        if (demande instanceof DemandeConge demandeConge) {

            double joursDemandes = demandeConge.getNbrJours();

            empSer.utiliserJours(
                    demande.getEmploye().getIdE(),
                    joursDemandes
            );
        }
        demande.setStatut(StatutEnum.APPROUVEE);
        notificationService.creerNotification(demande.getEmploye(),
                "Votre demande du " + demande.getDateDebut() + " au " + demande.getDateFin() + " a été approuvée !");
    }

    public void refuserParRH(Demande demande) {
        if (demande.getStatut() != StatutEnum.EN_ATTENTE_RH) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cette demande n'est pas en attente de validation RH");
        }
        demande.setStatut(StatutEnum.REFUSEE);
        notificationService.creerNotification(demande.getEmploye(),
                "Votre demande du " + demande.getDateDebut() + " au " + demande.getDateFin() + " a été refusée par le RH.");
    } 
    
}
