package com.med.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = MedRangeInfo.class)
public class MedRangeInfo {

    public String pId;

    public String medName;

    public String action;

    public Date eventTime;


    public MedRangeInfo(String pId,String medName,String action,Date eventTime){
        this.pId = pId;
        this.medName = medName;
        this.action = action;
        this.eventTime = eventTime;
    }
    @Override
    public String toString() {
        return "MedRange info  [patient-id=" + pId + ", medicine=" + medName +  ", action=" + action + ", eventTime=" + eventTime + "]";
    }

}
