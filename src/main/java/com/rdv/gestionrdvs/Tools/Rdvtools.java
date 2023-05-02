package com.rdv.gestionrdvs.Tools;

import com.rdv.gestionrdvs.Services.Ipatient;
import com.rdv.gestionrdvs.Services.Irdv;
import com.rdv.gestionrdvs.entities.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class Rdvtools {
    private final Ipatient ipatient;
    private final Irdv irdv;
    public Long CountAppointementsForDoctor(LocalDateTime dateTime , Doctor doctor){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDateTime day_start =
                LocalDateTime.parse(dateTime.format(formatter) +"T00:00:00",
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime day_end =
                LocalDateTime.parse(dateTime.format(formatter) +"T23:59:59",
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Long number_of_Appointments;
        number_of_Appointments =irdv.countRdv(day_start, day_end , doctor.getUser_id());
        return number_of_Appointments;
    }

}
