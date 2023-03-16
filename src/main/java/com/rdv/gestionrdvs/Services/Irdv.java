package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Rdv;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Irdv {
    Rdv addRdv(Rdv rdv);
    Optional<Rdv> findRdvById(Long id);
    void deleteRdv(Long id);
    List<Rdv> listerRdv();
    void cancelRdv(Long id);


    Long countRdv(LocalDateTime start, LocalDateTime end,Long doctor_id);
    List<Rdv> listRdvbyDoctor(Doctor doctor);
}
