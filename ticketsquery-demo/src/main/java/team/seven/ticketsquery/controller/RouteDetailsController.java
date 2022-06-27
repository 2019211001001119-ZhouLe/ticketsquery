package team.seven.ticketsquery.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.domain.RouteDetails;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.service.RouteDetailsService;
import team.seven.ticketsquery.vo.RouteDetailsVo;

import java.util.Arrays;
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
    @RequestMapping(value = "/details/{routertrainId}", method = RequestMethod.GET)
    ResultVO<?> getDetailsList(@PathVariable String routertrainId) {
        QueryWrapper<RouteDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("routertrain_id", routertrainId);
        return new ResultVO<>(service.list(wrapper));
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
    @RequestMapping(value = "/details/{rdID}/{routertrainId}", method = RequestMethod.PUT)
    ResultVO<?> lateDetail(@PathVariable String rdID, @PathVariable String routertrainId , @RequestBody RouteDetails routeDetails) throws Exception {
        int routerdetailId = Integer.parseInt(rdID);
        if (!routeDetails.getRouterdetailId().equals(routerdetailId) || !routeDetails.getRoutertrainId().equals(routertrainId))
            throw new Exception();
        return new ResultVO<>(
                service.detailLaterSet(routeDetails.getLaterTime(), routerdetailId, routertrainId) > 0 ?  ResultStatusEnum.SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    @RequestMapping(value = "/details/", method = RequestMethod.PUT)
    ResultVO<?> updDetail(@RequestBody RouteDetails routeDetails) throws Exception {
        return new ResultVO<>(
                service.updateOneDetail(routeDetails) > 0 ?  ResultStatusEnum.SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //删除经停信息
    //返回code为204
    @RequestMapping(value = "/details/{rdID}/{routertrainId}", method = RequestMethod.DELETE)
    ResultVO<?> delDetail(@PathVariable String rdID, @PathVariable String routertrainId) {
        int routerdetailId = Integer.parseInt(rdID);
        return new ResultVO<>(
                service.deleteOneDetail(routerdetailId, routertrainId) > 0 ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //分页查询
    @RequestMapping(value = "/detailspage", method = RequestMethod.GET)
    ResultVO<?> detailsPage(@RequestParam(value = "current", required = false) int current, @RequestParam(value = "size", required = false) int size) {
        Page<RouteDetails> routeDetailsPage = new Page<>(current, size);
        Page<RouteDetails> page = new LambdaQueryChainWrapper<>(service.getBaseMapper()).page(routeDetailsPage);
        return new ResultVO<>(page);
    }
    //新闻管理模块 -- 经停站信息
    @RequestMapping(value = "/newsdetails", method = RequestMethod.GET)
    public Page<RouteDetailsVo> getTrainStationList(@RequestParam(value = "current", required = false) Integer current,
                                                    @RequestParam(value = "size", required = false) Integer size) {
        Page<RouteDetailsVo> page = new Page<>(current, size);
        return service.RouteDetailsList(page);

    }

    //新闻管理模块 -- 通过车次号查询
    @RequestMapping(value = "/newdetailsbycondition", method = RequestMethod.GET)
    Page<RouteDetailsVo> queryByTrainStationName(@Param("current") Integer current,
                                                 @Param("size")    Integer size,
                                                 @Param("routertrain_id") String routertrainId) {
        long total = 0;
        Page page = new Page<>(current,size, total);
        return service.queryAllByRoutertrainId(page,routertrainId);
    }

    //批量删除车站
    @RequestMapping(value = "/newdetails/{routerdetailId}", method = RequestMethod.DELETE)
    ResultVO<?> delTrainStations(@PathVariable String[] routerdetailId) {
        return new ResultVO<>(
                service.removeByIds(Arrays.asList(routerdetailId)) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }
}
