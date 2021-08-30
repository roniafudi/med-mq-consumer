package com.med.persistence.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by roniaf on 18/08/2018.
 */
@Entity
@Table(name = "med_range")
public class MedChange {

    @Id
    @GeneratedValue
    @Column(name = "id" , unique = true)
    private Long id;

    @Column(name = "patient_id" )
    private String patientId;

    @Column(name = "med_name")
    private String medName;

    @Column(name="action")
    private String action;

    @Column(name = "event_time")
    private Date eventTime;

    public MedChange(String pId, String medName, String action, Date eventTime){
        this.patientId = pId;
        this.medName = medName;
        this.action = action;
        this.eventTime = eventTime;
    }

    public MedChange(){}

    public Long getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedName() {
        return medName;
    }

    public String getAction() {
        return action;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public Long getEventTimeInMillis() {
        return eventTime.getTime();
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
}
