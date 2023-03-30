package com.asteriosoft.lukyanau.testingtask.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_records")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class LogRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_records_id_seq")
    @SequenceGenerator(name = "log_records_id_seq", sequenceName = "log_records_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_ip_address")
    private String requestIpAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Column(name = "selected_banner_id")
    private Long selectedBannerId;

    @Column(name = "selected_banner_category_ids")
    private String selectedBannerCategoryIds;

    @Column(name = "selected_banner_price")
    private BigDecimal selectedBannerPrice;

    @Column(name = "no_content_reason")
    private String noContentReason;

}

