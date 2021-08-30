package com.med.persistence.repositories;

import com.med.persistence.entities.MedChange;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MedRangeRepository extends PagingAndSortingRepository<MedChange,Long> {

    List<MedChange> findAllByPatientId(String pid);
    List<MedChange> findAllByMedName(String pid);
    List<MedChange> findAllByPatientIdAndMedNameAndActionAndEventTime(String pid, String medName, String action, Date eventTime);

}

