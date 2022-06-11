package team.seven.ticketsquery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.mapper.RouteDetailsMapper;
import team.seven.ticketsquery.service.RouteDetailsService;

/**
 * @description: 经停信息Controller
 * @author: ZhouLe
 * @create: 2022-06-11
 * @version: 1.0
 */
@RestController
public class RouteDetailsController {
    @Autowired
    RouteDetailsMapper mapper;
    private final RouteDetailsService service;
    RouteDetailsController(RouteDetailsService service) {
        this.service = service;
    }
    @RequestMapping(value = "/details/{routertrainId}", method = RequestMethod.GET)
    ResultVO<?> getDetailsList(@PathVariable String routertrainId) {
        return new ResultVO<>(mapper.findTrainStationNameByRouteID(routertrainId));
    }
}
