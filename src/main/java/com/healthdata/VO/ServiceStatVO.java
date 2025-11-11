package com.healthdata.VO;

import lombok.Data;

@Data
public class ServiceStatVO {
    private Long outpatientVisits;
    private Long inpatientAdmissions;
    private String label;
}