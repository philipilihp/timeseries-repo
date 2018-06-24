package de.philipilihp.timeseriesrepo.rest;

import de.philipilihp.timeseriesrepo.repositories.TimeseriesRepository;
import de.philipilihp.timeseriesrepo.repositories.TimeseriesValue;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log
@RestController
public class TimeseriesController {

    @Autowired
    private TimeseriesRepository timeseriesRepository;

    @RequestMapping("/timeseries")
    public List<TimeseriesValue> findAll() {
        return timeseriesRepository.findAll();
    }

    @RequestMapping("/timeseries/{name}/{version}")
    public Timeseries findById(@PathVariable(value="name") String name,
                               @PathVariable(value="version") String version,
                               @RequestParam(name = "from", required = false) Instant from,
                               @RequestParam(name = "to", required = false) Instant to) {

        List<TimeseriesValue> values = Collections.emptyList();
        if (from == null || to == null) {
            values = timeseriesRepository.findByNameAndVersion(name, version);
        } else {
            values = timeseriesRepository.findByNameAndVersionAndTimeAfterAndTimeBefore(name, version, from, to);
        }

        Timeseries timeseries = new Timeseries(name, version);
        values.stream()
                .forEach(value -> timeseries.getValues().put(
                        value.getTime(), value.getValue()));

        return timeseries;
    }

    @RequestMapping("/generate/{count}")
    public String generate(@PathVariable(value="count") int count) {

        log.info("start generation ...");

        List<TimeseriesValue> values = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Instant start = Instant.parse("2018-01-01T00:00:00.00Z");
            Instant end = Instant.parse("2019-01-01T00:00:00.00Z");

            String name = "t" + i;
            String version = "v1";
            while(start.isBefore(end)) {
                TimeseriesValue timeseriesValue = new TimeseriesValue();
                timeseriesValue.setName(name);
                timeseriesValue.setVersion(version);
                timeseriesValue.setTime(start);
                timeseriesValue.setValue(new BigDecimal(Math.random()).setScale(2, RoundingMode.HALF_UP));
                start = start.plus(15, ChronoUnit.MINUTES);
                values.add(timeseriesValue);
            }
        }

        System.out.println("write " + values.size() + " to cassandra...");
        long startTime = System.currentTimeMillis();

        timeseriesRepository.insert(values);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = (stopTime - startTime) / 1000;

        return count + " timeseries written to Cassandra in " + elapsedTime + " seconds";
    }

}
