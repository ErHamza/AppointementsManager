package com.rdv.gestionrdvs.Repositories;

import com.rdv.gestionrdvs.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
//    Speciality findSpecialityBySpeciality_name(String name);

}
