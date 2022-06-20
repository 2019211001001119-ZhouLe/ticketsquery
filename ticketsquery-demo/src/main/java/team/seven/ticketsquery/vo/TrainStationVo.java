package team.seven.ticketsquery.vo;


/*
 * @author: lew
 * @date:  2022/6/17
 * @version: 1.8
 * @description: 该vo类包括车站实体类及省份名、市名等属性
 */

import lombok.Data;
import team.seven.ticketsquery.domain.TrainStation;

@Data
public class TrainStationVo extends TrainStation {

    private String provinceName;
    private String cityName;
}