package de.philipilihp.timeseriesrepo.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.Instant;
import java.util.List;

public interface TimeseriesRepository extends CassandraRepository<TimeseriesValue, String> {

    List<TimeseriesValue> findByNameAndVersion(String name, String version);

    List<TimeseriesValue> findByNameAndVersionAndTimeAfterAndTimeBefore(String name, String version, Instant from, Instant to);

}
