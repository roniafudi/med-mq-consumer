package com.med.services;

import com.med.model.PatientMedPeriod;
import com.med.persistence.entities.MedChange;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MedPeriodsBuilder {


    public List<PatientMedPeriod> build(List<MedChange> events) {
        return buildPeriods(events);
    }

    private List<PatientMedPeriod> buildPeriods(List<MedChange> events) {
        List<PatientMedPeriod> res = new ArrayList<>();
        Stack<MedChange> stack = new Stack<>();

        for(MedChange evt: events) {
            switch (evt.getAction()) {
                case ("start"):
                    if (stack.isEmpty())
                        stack.push(evt);
                    else if(stack.size()==2) {
                        MedChange to = stack.pop();
                        MedChange from = stack.pop();
                        res.add(createMedPeriod(from,to));
                        stack.push(evt);
                    }
                    break;
                case ("stop"):
                    if (stack.size() == 1) {
                        stack.push(evt);
                    }else if(stack.size()==2) {
                        stack.pop();
                    }
                    break;
                case ("cancel_start"):
                    if (!stack.isEmpty()) {
                        stack.clear();
                    }
                    break;
                case ("cancel_stop"):
                    if (!stack.isEmpty() && stack.size()==2) {
                        stack.pop();
                    }
                    break;
            }

        }

        if(stack.size() == 2){
            MedChange to = stack.pop();
            MedChange from = stack.pop();
            res.add(createMedPeriod(from,to));
        }
        return res;
    }

    private PatientMedPeriod createMedPeriod(MedChange start, MedChange end) {
        return new PatientMedPeriod(start.getPatientId(),start.getMedName(),start.getEventTime(),end.getEventTime());
    }


    //handle cancel_start and cancel_stop events as well as start/stop for the same event_time
    private List<MedChange> filterCancelEvents(List<MedChange> events) {


        return events;
    }
}
