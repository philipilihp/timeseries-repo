package de.philipilihp.timeseriesrepo.repositories;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Table("timeseries")
public class TimeseriesValue {

    @PrimaryKeyColumn(
            name = "name",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED)
    private String name;

    @PrimaryKeyColumn(
            name = "version",
            ordinal = 2,
            type = PrimaryKeyType.PARTITIONED)
    private String version;

    @PrimaryKeyColumn(
            name = "time",
            ordinal = 3,
            type = PrimaryKeyType.CLUSTERED)
    private Instant time;

    @Column
    private BigDecimal value;


}
