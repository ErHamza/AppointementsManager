package com.rdv.gestionrdvs.Repositories;

import com.rdv.gestionrdvs.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {


    Optional<Doctor> findDoctorByEmail(String email);


    Optional<Doctor> findDoctorBySpeciality(String speciality);
}
