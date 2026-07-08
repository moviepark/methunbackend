package com.example.demo.dto;

import java.util.Map;

public class DistrictMetricsDto {
    private String district;
    private Long totalRegisteredVoters;
    private Double averageSupportScore;
    private Map<String, Long> sentimentDistribution;

    public DistrictMetricsDto() {}

    public DistrictMetricsDto(String district, Long totalRegisteredVoters, Double averageSupportScore, Map<String, Long> sentimentDistribution) {
        this.district = district;
        this.totalRegisteredVoters = totalRegisteredVoters;
        this.averageSupportScore = averageSupportScore;
        this.sentimentDistribution = sentimentDistribution;
    }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public Long getTotalRegisteredVoters() { return totalRegisteredVoters; }
    public void setTotalRegisteredVoters(Long totalRegisteredVoters) { this.totalRegisteredVoters = totalRegisteredVoters; }
    public Double getAverageSupportScore() { return averageSupportScore; }
    public void setAverageSupportScore(Double averageSupportScore) { this.averageSupportScore = averageSupportScore; }
    public Map<String, Long> getSentimentDistribution() { return sentimentDistribution; }
    public void setSentimentDistribution(Map<String, Long> sentimentDistribution) { this.sentimentDistribution = sentimentDistribution; }
}
