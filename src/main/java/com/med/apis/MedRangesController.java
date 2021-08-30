package com.med.apis;

import com.med.model.PatientMedPeriod;
import com.med.persistence.entities.MedChange;
import com.med.persistence.repositories.MedRangeRepository;
import com.med.services.MedPeriodsProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedRangesController {
    final MedRangeRepository repo;
    final MedPeriodsProcessor processor;

    public MedRangesController(MedPeriodsProcessor processor, MedRangeRepository repo) {
        this.processor = processor;
        this.repo = repo;
    }

    @GetMapping("/api/med-ranges/{id}")
    @ResponseBody
    public List<PatientMedPeriod> getMedRangesByMedName(@PathVariable("id") String pid) {

        List<PatientMedPeriod> details = processor.getPatientMedPeriods(pid);
        return details;
    }
}
