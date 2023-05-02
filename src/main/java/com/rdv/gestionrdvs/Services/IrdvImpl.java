package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.Repositories.RdvRepository;
import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Patient;
import com.rdv.gestionrdvs.entities.Rdv;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
//@Transactional
public class IrdvImpl implements Irdv {

    private RdvRepository rdvRepository;
    @Override
    public Rdv addRdv(Rdv rdv) {

//        System.out.println(rdvRepository.countByDate_rdvLikeAndDoctor
//                ( date,
//                 rdv.getDoctor()
//                ));

        return rdvRepository.save(rdv) ;
    }

    @Override
    public Optional<Rdv> findRdvById(Long id) {
        return rdvRepository.findById(id);
    }

    @Override
    public void deleteRdv(Long id) {

    }
    @Override
    public List<Rdv> listerRdv() {

        return rdvRepository.findAll();
    }

    @Override
    public void cancelRdv(Long id) {
        Rdv rdv = findRdvById(id).orElse(null);
        assert rdv!= null;
        rdv.setIsCanceled(true);
    }



    @Override
    public Long countRdv(LocalDateTime start, LocalDateTime end , Long doctor_id) {
        return rdvRepository.countByDateRdv(start, end , doctor_id);
    }

    @Override
    public List<Rdv> listRdvbyDoctor(Doctor doctor)
    {
    return rdvRepository.findByDoctorAndIsDoneAndIsCanceled(doctor, false , false);
    }

    @Override
    public List<Rdv> listRdvByPatient(Patient patient) {
        return rdvRepository.findByPatient(patient);
    }
}
