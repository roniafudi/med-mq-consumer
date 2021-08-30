package com.med.cunsumer;

import com.med.CunsumerApplication;
import com.med.apis.MedPeriodsController;
import com.med.model.MedRangeInfo;
import com.med.model.PatientMedPeriod;
import com.med.persistence.repositories.MedPeriodsRepository;
import com.med.services.MedChangeEventWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {CunsumerApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CunsumerApplicationTests {

    @Autowired
    MedChangeEventWriter processor;

    @Autowired
    MedPeriodsRepository repo;

    @Autowired
    MedPeriodsController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

   private List<MedRangeInfo> data = new ArrayList<MedRangeInfo>();


    @Test
    void testSinglePeriod() throws ParseException {
        repo.deleteAll();
        data.add(new MedRangeInfo("1","x","start",getDate("2021-01-01T00:00:00+0000")));
        data.add(new MedRangeInfo("1","x","stop",getDate("2021-01-01T01:00:00+0000")));

        data.stream().forEach(info -> processor.processMedRange(info));

        List<PatientMedPeriod> res = controller.getClientMedPeriods("1");
        assert(res.size() == 1);

    }

    @Test
    void testEventsForMultipleUsers() throws ParseException {
        repo.deleteAll();
        data.add(new MedRangeInfo("1","x","start",getDate("2021-01-01T00:00:00+0000")));
        data.add(new MedRangeInfo("1","x","stop",getDate("2021-01-01T01:00:00+0000")));
        data.add(new MedRangeInfo("2","y","start",getDate("2021-01-01T00:00:00+0000")));
        data.add(new MedRangeInfo("2","y","stop",getDate("2021-01-01T01:00:00+0000")));
        data.add(new MedRangeInfo("2","y","start",getDate("2021-01-01T02:00:00+0000")));
        data.add(new MedRangeInfo("2","y","stop",getDate("2021-01-01T03:00:00+0000")));

        data.stream().forEach(info -> processor.processMedRange(info));

        List<PatientMedPeriod> res_1 = controller.getClientMedPeriods("1");
        assert(res_1.size() == 1);
        List<PatientMedPeriod> res_2 = controller.getClientMedPeriods("2");
        assert(res_2.size() == 2);

    }

    @Test
    void testStartAndCancel() throws ParseException {
        repo.deleteAll();
        data.add(new MedRangeInfo("1","x","start",getDate("2021-01-01T00:00:00+0000")));
        data.add(new MedRangeInfo("1","x","cancel_start",getDate("2021-01-01T00:00:00+0000")));

        data.stream().forEach(info -> processor.processMedRange(info));

        List<PatientMedPeriod> res = controller.getClientMedPeriods("1");
        assert(res.size() == 0);

    }


    @Test
    void testCancelBeforeStart() throws ParseException {
        repo.deleteAll();
        data.add(new MedRangeInfo("1","x","stop",getDate("2021-01-01T00:00:00+0000")));
        data.add(new MedRangeInfo("1","x","start",getDate("2021-01-01T03:00:00+0000")));
        data.add(new MedRangeInfo("1","x","stop",getDate("2021-01-01T04:00:00+0000")));

        data.stream().forEach(info -> processor.processMedRange(info));

        List<PatientMedPeriod> res = controller.getClientMedPeriods("1");
        assert(res.size() == 1);

    }

    @Test
    void testEmptyResults() {
        repo.deleteAll();

        List<PatientMedPeriod> res = controller.getClientMedPeriods("undefined");
        assert(res.size() == 0);

    }

    @Test
    void test2ValidPeriods() throws ParseException {
        repo.deleteAll();
        data.add(new MedRangeInfo("1","x","start",getDate("2021-01-01T00:00:00+0000")));
        data.add(new MedRangeInfo("1","x","stop",getDate("2021-01-01T01:00:00+0000")));
        data.add(new MedRangeInfo("1","x","start",getDate("2021-01-02T00:00:00+0000")));
        data.add(new MedRangeInfo("1","x","stop",getDate("2021-01-02T01:00:00+0000")));

        data.stream().forEach(info -> processor.processMedRange(info));

        List<PatientMedPeriod> res = controller.getClientMedPeriods("1");
        assert(res.size() == 2);

    }


    private Date getDate(String s) throws ParseException {
        SimpleDateFormat fmt =new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        return fmt.parse(s);
    }
}
