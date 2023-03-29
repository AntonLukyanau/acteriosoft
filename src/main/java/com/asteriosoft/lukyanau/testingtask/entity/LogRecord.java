package com.asteriosoft.lukyanau.testingtask.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "log_records")
@NoArgsConstructor
@Getter
@Setter
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
    private Double selectedBannerPrice;

    @Column(name = "no_content_reason")
    private String noContentReason;

    public static LogRecordBuilder builder() {
        return new LogRecordBuilder();
    }

    public static class LogRecordBuilder {
        private Long id;
        private String requestIpAddress;
        private String userAgent;
        private LocalDateTime requestTime;
        private Long selectedBannerId;
        private String selectedBannerCategoryIds;
        private Double selectedBannerPrice;
        private String noContentReason;

        LogRecordBuilder() {
        }

        public LogRecordBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LogRecordBuilder requestIpAddress(String requestIpAddress) {
            this.requestIpAddress = requestIpAddress;
            return this;
        }

        public LogRecordBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public LogRecordBuilder requestTime(LocalDateTime requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public LogRecordBuilder selectedBannerId(Long selectedBannerId) {
            this.selectedBannerId = selectedBannerId;
            return this;
        }

        public LogRecordBuilder selectedBannerCategoryIds(String selectedBannerCategoryIds) {
            this.selectedBannerCategoryIds = selectedBannerCategoryIds;
            return this;
        }

        public LogRecordBuilder selectedBannerPrice(Double selectedBannerPrice) {
            this.selectedBannerPrice = selectedBannerPrice;
            return this;
        }

        public LogRecordBuilder noContentReason(String noContentReason) {
            this.noContentReason = noContentReason;
            return this;
        }

        public LogRecord build() {
            return new LogRecord(id, requestIpAddress, userAgent, requestTime, selectedBannerId, selectedBannerCategoryIds, selectedBannerPrice, noContentReason);
        }
    }
}

