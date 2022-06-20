package team.seven.ticketsquery.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@CrossOrigin
@RestController
public class RouteDetailsController {
    private final RouteDetailsService service;
    RouteDetailsController(RouteDetailsService service) {
        this.service = service;
    }

    //所有经停信息
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    ResultVO<?> getDetailsList() {
        return new ResultVO<>(service.list());
    }

    //根据车次号查询经停信息
    @RequestMapping(value = "/details/query/{routertrainId}", method = RequestMethod.GET)
    ResultVO<?> DetailListById(@PathVariable String routertrainId) {
        return new ResultVO<>(service.findDetailsByRouteID(routertrainId));
    }

    //根据车次id和车站id查询某条经停信息
    @RequestMapping(value = "/details/{routertrainId}/{trainstationId}", method = RequestMethod.GET)
    ResultVO<?> oneDetail(@PathVariable String routertrainId, @PathVariable String trainstationId) {
        return new ResultVO<>(service.getOneByTIdAndSId(routertrainId, trainstationId));
    }

    //新增一条经停信息
    //返回code为201
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    ResultVO<?> addOneDetail(@RequestBody RouteDetails routeDetails) {
        return new ResultVO<>(service.save(routeDetails) ? ResultStatusEnum.CREATE_SUCCESS : ResultStatusEnum.BAD_REQUEST);
    }

    //更新经停信息
    @RequestMapping(value = "/details/{routertrainId}/{trainstationId}", method = RequestMethod.PUT)
    ResultVO<?> updDetail(@PathVariable String routertrainId, @PathVariable String trainstationId , @RequestBody RouteDetails routeDetails) throws Exception {
        if (!routeDetails.getRoutertrainId().equals(routertrainId) || !routeDetails.getTrainstationId().equals(trainstationId))
            throw new Exception();
        return new ResultVO<>(
                service.updateById(routeDetails)?  ResultStatusEnum.SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //删除经停信息
    //返回code为204
    @RequestMapping(value = "/details/{routertrainId}/{trainstationId}", method = RequestMethod.DELETE)
    ResultVO<?> delDetail(@PathVariable String routertrainId, @PathVariable String trainstationId) {
        return new ResultVO<>(
                service.removeById(service.getOneByTIdAndSId(routertrainId, trainstationId)) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //分页查询
    @RequestMapping(value = "/detailspage", method = RequestMethod.GET)
    ResultVO<?> detailsPage(@RequestParam int current, @RequestParam int size) {
        Page<RouteDetails> routeDetailsPage = new Page<>(current, size);
        Page<RouteDetails> page = new LambdaQueryChainWrapper<>(service.getBaseMapper()).page(routeDetailsPage);
        return new ResultVO<>(page);
    }
}
