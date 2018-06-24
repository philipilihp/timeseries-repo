package de.philipilihp.timeseriesrepo.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.SortedMap;
import java.util.TreeMap;

@Getter
@Setter
@NoArgsConstructor
public class Timeseries {

    private String name;
    private String version;
    private SortedMap<Instant, BigDecimal> values = new TreeMap<>();

    public Timeseries(String name, String version) {
        this.name = name;
        this.version = version;
    }

}
