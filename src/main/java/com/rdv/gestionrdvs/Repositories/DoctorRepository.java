package com.rdv.gestionrdvs.Repositories;

import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {


    Optional<Doctor> findDoctorByEmail(String email);


    List<Doctor> findDoctorBySpeciality(Speciality speciality);
}
