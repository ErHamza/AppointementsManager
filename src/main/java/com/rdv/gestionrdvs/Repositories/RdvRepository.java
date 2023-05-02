package com.rdv.gestionrdvs.Repositories;

import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Patient;
import com.rdv.gestionrdvs.entities.Rdv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RdvRepository extends JpaRepository<Rdv, Long> {

    List<Rdv> findByPatientAndIsDoneAndIsCanceled(Patient patient, Boolean isDone, Boolean isCanceled);
    List<Rdv> findByPatient(Patient patient);
    List<Rdv> findByDoctor(Doctor doctor);
    List<Rdv> findByDoctorAndIsDoneAndIsCanceled(Doctor doctor, Boolean isDone , Boolean isCanceld);

@Query("SELECT COUNT(e) FROM Rdv e WHERE  e.date_rdv > ?1 and e.date_rdv < ?2 and e.doctor.user_id= ?3 ")
    long countByDateRdv(LocalDateTime start, LocalDateTime end , Long doctor_id);





}
