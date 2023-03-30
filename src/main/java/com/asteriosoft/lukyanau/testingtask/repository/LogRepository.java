package com.asteriosoft.lukyanau.testingtask.repository;

import com.asteriosoft.lukyanau.testingtask.entity.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<LogRecord, BigInteger> {

    List<LogRecord> findByRequestIpAddressAndUserAgentAndRequestTimeAfter(
            String ipAddress,
            String userAgent,
            LocalDateTime dateTime);

}
