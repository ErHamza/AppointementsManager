package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.Repositories.SpecialityRepository;
import com.rdv.gestionrdvs.entities.Speciality;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IspecialityImpl implements Ispeciality {
    private final SpecialityRepository specialityRepository;
    @Override
    public Speciality addSpeciality(Speciality speciality) {
        return specialityRepository.save(speciality) ;
    }

    @Override
    public Speciality findById(Long id) {
        return specialityRepository.findById(id).orElse(null);
    }

//    @Override
//    public Speciality findByName(String name) {
//        return null;
//    }
//
//    @Override
//    public Speciality findByName(String name) {
////        return specialityRepository.findSpecialityBySpeciality_name(name);
//    }

    @Override
    public List<Speciality> findAll() {
        return specialityRepository.findAll();
    }
}
