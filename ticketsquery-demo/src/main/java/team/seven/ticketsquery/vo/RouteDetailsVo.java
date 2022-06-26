package team.seven.ticketsquery.vo;


/*
 * @author: lew
 * @date:  2022/6/22
 * @version: 1.8
 * @description:
 */

import lombok.Data;
import team.seven.ticketsquery.domain.RouteDetails;

@Data
public class RouteDetailsVo extends RouteDetails {
    String trainstationName;
    String adminName;


}