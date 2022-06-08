package com.seven.ticketsquery.domain;

import lombok.Data;

/**
 * @version 1.8
 * @date 2022/6/8 15:44
 */

@Data
public class TrainStation {
    private String trainstationId;
    private Integer cityId;
    private String trainstationName;
}
