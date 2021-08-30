package com.med.services;

import com.med.model.PatientMedPeriod;
import com.med.persistence.entities.MedChange;
import com.med.persistence.repositories.MedRangeRepository;
import org.springframework.stereotype.Component;
import java.util.*;


import static java.util.stream.Collectors.groupingBy;


@Component
public class MedPeriodsProcessor {

    private final MedRangeRepository repo;
    private final MedPeriodsBuilder builder;

    public MedPeriodsProcessor(MedRangeRepository repo,MedPeriodsBuilder builder) {
        this.repo = repo;
        this.builder = builder;
    }


    public List<PatientMedPeriod> getPatientMedPeriods(String pId) {
        List<PatientMedPeriod> periods = new ArrayList<>();

        List<MedChange> data = repo.findAllByPatientId(pId);

        Map<String,List<MedChange>> eventsByMed = data.stream().
                sorted(Comparator. comparingLong(MedChange::getEventTimeInMillis)).
                collect(groupingBy(MedChange::getMedName));

        for (List<MedChange> list : eventsByMed.values()) {
            periods.addAll(builder.build(list));
        }
        return periods;

    }

}
