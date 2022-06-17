package team.seven.ticketsquery.controller;

import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.domain.RouteDetails;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.service.RouteDetailsService;

import java.util.Date;

/**
 * @description: 经停信息Controller
 * @author: ZhouLe
 * @create: 2022-06-11
 * @version: 1.0
 */
@RestController
public class RouteDetailsController {
    private final RouteDetailsService service;
    RouteDetailsController(RouteDetailsService service) {
        this.service = service;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    ResultVO<?> getDetailsList() {
        return new ResultVO<>(service.list());
    }

    @RequestMapping(value = "/details/{routertrainId}", method = RequestMethod.GET)
    ResultVO<?> trainStationNameList(@PathVariable String routertrainId) {
        return new ResultVO<>(service.trainNameList(routertrainId));
    }

    @RequestMapping(value = "/details/{routertrainId}/{trainstationId}", method = RequestMethod.GET)
    ResultVO<?> oneDetail(@PathVariable String routertrainId, @PathVariable String trainstationId) {
        return new ResultVO<>(service.getOneByTIdAndSId(routertrainId, trainstationId));
    }

    @RequestMapping(value = "/details/{routertrainId}/{trainstationId}", method = RequestMethod.PUT)
    ResultVO<?> updDetail(@PathVariable String routertrainId, @PathVariable String trainstationId , @RequestBody RouteDetails routeDetails) throws Exception {
        if (!routeDetails.getRoutertrainId().equals(routertrainId) || !routeDetails.getTrainstationId().equals(trainstationId))
            throw new Exception();
        return new ResultVO<>(
                service.updateById(routeDetails)?  ResultStatusEnum.SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    @RequestMapping(value = "/details/{routertrainId}/{trainstationId}", method = RequestMethod.DELETE)
    ResultVO<?> delDetail(@PathVariable String routertrainId, @PathVariable String trainstationId) {
        return new ResultVO<>(
                service.removeById(service.getOneByTIdAndSId(routertrainId, trainstationId)) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    @RequestMapping(value = "/details/{start}/{end}", method = RequestMethod.GET)
    ResultVO<?> startToEndTrainNumber(@PathVariable String start, @PathVariable String end) {
        
        return new ResultVO<>();
    }

}
