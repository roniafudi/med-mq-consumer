package com.med.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = PatientMedPeriod.class)
public class PatientMedPeriod {

    public String pId;

    public String medName;

    public Date startDate;

    public Date endDate;


    public PatientMedPeriod(String pId, String medName, Date startDate, Date endDate){
        this.pId = pId;
        this.medName = medName;
        this.startDate = startDate;
        this.endDate = endDate;
    }


}
