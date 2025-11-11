package com.healthdata.VO;

import lombok.Data;

@Data
public class NationLatestVO {
    private Integer year;
    private BaseStatVO population;
    private BaseStatVO institution;
    private BaseStatVO personnel;
    private BaseStatVO bed;
    private ServiceStatVO service;
    private BaseStatVO cost;
}