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

    @RequestMapping(value = "/details/{routerdetailId}", method = RequestMethod.PUT)
    ResultVO<?> updDetail(@PathVariable String routerdetailId, @RequestBody RouteDetails routeDetails) {
        if (routeDetails.getRouterdetailId() != Integer.parseInt(routerdetailId)) {
            return new ResultVO<>(ResultStatusEnum.UNAUTHORIZED);
        }
        return new ResultVO<>(
                service.updateById(routeDetails) ? ResultStatusEnum.SUCCESS :ResultStatusEnum.NOT_FOUND
        );
    }

    @RequestMapping(value = "/details/{routerdetailId}", method = RequestMethod.DELETE)
    ResultVO<?> delDetail(@PathVariable String routerdetailId) {
        return new ResultVO<>(
                service.removeById(routerdetailId) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

}
