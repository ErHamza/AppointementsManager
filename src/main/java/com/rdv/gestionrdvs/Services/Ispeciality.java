package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.entities.Speciality;

import java.util.List;

public interface Ispeciality {
    Speciality addSpeciality (Speciality speciality);
    Speciality findById(Long id);
//    Speciality findByName(String name);
    List<Speciality> findAll();

}
