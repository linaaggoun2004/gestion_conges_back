package com.example.gestion_conges_back.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.DemandeArretRequest;
import com.example.gestion_conges_back.DTO.DemandeManResponse;
import com.example.gestion_conges_back.DTO.DemandeResponse;
import com.example.gestion_conges_back.entity.DemandeArretMaladie;
import com.example.gestion_conges_back.entity.Document;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.StatutEnum;
import com.example.gestion_conges_back.entity.TypeDemandeEnum;
import com.example.gestion_conges_back.repository.DemandeArretMaladierepository;
import com.example.gestion_conges_back.repository.Employerepository;

@Service
public class DemandeArretMaladieService {

    private final DemandeArretMaladierepository demandeArretRep;
    private final Employerepository employeRep;

    private static final String DOSSIER_UPLOAD = "C:/Users/linaa/Desktop/stage2emeProjet/gestion-conges-back/uploads/certificats/";

    public DemandeArretMaladieService(DemandeArretMaladierepository demandeArretRep, Employerepository employeRep) {
        this.demandeArretRep = demandeArretRep;
        this.employeRep = employeRep;
    }

    public String creerDemandeArret(DemandeArretRequest request, MultipartFile certificat, Long empId)
            throws IOException {

        Employe emp = this.employeRep.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        DemandeArretMaladie demande = new DemandeArretMaladie();

        demande.setTypeDemande(TypeDemandeEnum.ARRET_MALADIE);
        demande.setDateDebut(LocalDate.parse(request.getDateDebut()));
        demande.setDateFin(LocalDate.parse(request.getDateFin()));
        demande.setDateCreation(LocalDateTime.now());
        demande.setEmploye(emp);

        demande.setStatut(StatutEnum.EN_ATTENTE_MANAGER);

        double nbr_jours = ChronoUnit.DAYS.between(LocalDate.parse(request.getDateDebut()),
                LocalDate.parse(request.getDateFin())) + 1;

        Integer d = (int) nbr_jours;
        demande.setDuree(d);
        demande.setMetadonnees(request.getMetadonnes());

        Document doc = enregistrerFichierSurDisque(certificat, demande);
        demande.getDocuments().add(doc);

        demandeArretRep.save(demande);

        return "la demande a bien ete creer";

    }

    private Document enregistrerFichierSurDisque(MultipartFile certificat, DemandeArretMaladie demande)
            throws IOException {
        Files.createDirectories(Paths.get(DOSSIER_UPLOAD));

        String nomOriginal = certificat.getOriginalFilename();
        String nomUnique = UUID.randomUUID() + "_" + nomOriginal;
        Path cheminComplet = Paths.get(DOSSIER_UPLOAD, nomUnique);

        certificat.transferTo(cheminComplet.toFile());

        Document doc = new Document();
        doc.setNomFichier(nomOriginal);
        doc.setDemandeArretMaladie(demande);
        doc.setChemin(cheminComplet.toString());
        doc.setDureeConservation(30);
        doc.setDateDepot(LocalDateTime.now());

        return doc;

    }

    public List<DemandeResponse> getAllDemandeArretIdE(Long employeId) {
        List <DemandeArretMaladie> demandes=demandeArretRep.findByEmploye_IdE(employeId);
        List<DemandeResponse> responses = new ArrayList<>();
        for (DemandeArretMaladie demande : demandes) {
             DemandeResponse response= new DemandeResponse(Double.valueOf(demande.getDuree()),
                demande.getMetadonnees(),
                demande.getDateCreation().toString(),
                demande.getDateDebut().toString(),
                demande.getDateFin().toString(),
                demande.getIdD(),
                demande.getStatut(),
                TypeDemandeEnum.ARRET_MALADIE,
                "Aucun types"
            );

        responses.add(response);
    }

    return responses;
}

    public List<DemandeArretMaladie> getAllDemandeArretMal() {
        return demandeArretRep.findAll();
    }

    public String annulerDemandeArretMal(Long id) {
        DemandeArretMaladie demande = getDemandeArretParId(id);

        demande.setStatut(StatutEnum.ANNULEE);

        demandeArretRep.save(demande);
        return "La demande est annulee avec succes";
    }

    public DemandeArretMaladie getDemandeArretParId(Long id) {
        return demandeArretRep.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Demande introuvable avec l'id : " + id));
    }

    public List<DemandeManResponse> getDemandesParManagerArret(Long idManager){

        List<Employe> emps=employeRep.findByManager_IdE(idManager);

        List<DemandeManResponse> responses=new ArrayList<>();
        List<DemandeArretMaladie> demandes=new ArrayList<>();

        for (Employe e:emps){
            List<DemandeArretMaladie> d=demandeArretRep.findByEmploye_IdE(e.getIdE());
            demandes.addAll(d);
        }
        for ( DemandeArretMaladie demande : demandes){
            String nomComplet=demande.getEmploye().getNom()+ " "+demande.getEmploye().getPrenom();
            DemandeManResponse response= new DemandeManResponse(
                demande.getIdD(),
                nomComplet,
                TypeDemandeEnum.ARRET_MALADIE,
                "Aucun type",
                demande.getDateDebut().toString(),
                demande.getDateFin().toString(),
                String.valueOf(demande.getDuree()),
                demande.getMetadonnees(),
                demande.getStatut(),
                demande.getDateCreation().toString(),
                demande.getEmploye().getPoste()
            );
            responses.add(response);
        }
        return responses;
    }

}
