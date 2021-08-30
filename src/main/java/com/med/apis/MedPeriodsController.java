package com.med.apis;

import com.med.model.PatientMedPeriod;
import com.med.persistence.repositories.MedPeriodsRepository;
import com.med.services.MedPeriodsProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedPeriodsController {
    final MedPeriodsRepository repo;
    final MedPeriodsProcessor processor;

    public MedPeriodsController(MedPeriodsProcessor processor, MedPeriodsRepository repo) {
        this.processor = processor;
        this.repo = repo;
    }

    @GetMapping("/api/med-periods/{id}")
    @ResponseBody
    public List<PatientMedPeriod> getClientMedPeriods(@PathVariable("id") String pid) {

        List<PatientMedPeriod> details = processor.getPatientMedPeriods(pid);
        return details;
    }
}
