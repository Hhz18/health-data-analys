package com.healthdata.VO;

import lombok.Data;

@Data
public class ServiceYearDataVO {
    private Integer year;
    private Long outpatientVisits;
    private Long inpatientAdmissions;
}