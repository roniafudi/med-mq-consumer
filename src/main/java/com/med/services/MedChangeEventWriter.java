package com.med.services;

import com.med.model.MedRangeInfo;
import com.med.persistence.entities.MedChange;
import com.med.persistence.repositories.MedPeriodsRepository;
import org.springframework.stereotype.Component;
import java.text.ParseException;

@Component
public class MedChangeEventWriter {

    private final MedPeriodsRepository repo;

    public MedChangeEventWriter(MedPeriodsRepository repo) {
        this.repo = repo;
    }

    public void processMedRange(MedRangeInfo mr)  {

        try{
          if(isDuplicate(mr)){
            System.out.println("following data already exists in the system - skipping ...." + mr);
          }else{
            MedChange ent = new MedChange(mr.pId,mr.medName,mr.action, mr.eventTime);
            repo.save(ent);
          }
        }catch (ParseException ex){
           System.out.println("Encountered an invalid date format when trying to parse date: " + mr.eventTime);
        }
    }

    private boolean isDuplicate(MedRangeInfo mr) throws ParseException {
        return repo.findAllByPatientIdAndMedNameAndActionAndEventTime(mr.pId, mr.medName,
                mr.action, mr.eventTime).size() > 0;
    }
}
