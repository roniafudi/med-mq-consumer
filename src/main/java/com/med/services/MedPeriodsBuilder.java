package com.med.services;

import com.med.model.MedRangeInfo;
import com.med.model.PatientMedPeriod;
import com.med.persistence.entities.MedChange;
import com.med.persistence.repositories.MedRangeRepository;
import com.med.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
public class MedPeriodsBuilder {


    public List<PatientMedPeriod> buildMedPeriods(List<MedChange> events) {
        List<MedChange> cleanEvents = filterCancelEvents(events);
        return buildPeriods(cleanEvents);
    }

    private List<PatientMedPeriod> buildPeriods(List<MedChange> cleanEvents) {
        List<PatientMedPeriod> res = new ArrayList<>();
        Stack<MedChange> stack = new Stack<>();

        for(MedChange evt: cleanEvents){
            if(evt.getAction().equals("start")){
                if(stack.isEmpty())
                    stack.push(evt);
            }else if(evt.getAction().equals("stop")){
                if(!stack.isEmpty()){
                    res.add(createMedPeriod(stack.pop(),evt));
                }
            }
        }
        return res;
    }

    private PatientMedPeriod createMedPeriod(MedChange start, MedChange end) {
        return new PatientMedPeriod(start.getPatientId(),start.getMedName(),start.getEventTime(),end.getEventTime());
    }


    //handle cancel_start and cancel_stop events as well as start/stop for the same event_time
    private List<MedChange> filterCancelEvents(List<MedChange> events) {

        Map<Date, List<MedChange>> eventsPerDateMap = events.stream().collect(groupingBy(MedChange::getEventTime));
        Set<Date> datesToRemove = new HashSet<>();
        for(Date eventTime : eventsPerDateMap.keySet()){
            //if 2 events fall on the same time they cancel each other (either start/stop_start or close/stop_close or start/stop)
            if(eventsPerDateMap.get(eventTime).size() == 2){
                datesToRemove.add(eventTime);
            }
        }

        return events.stream().filter(r -> datesToRemove.contains(r.getEventTime()) == false).collect(Collectors.toList());

    }
}
