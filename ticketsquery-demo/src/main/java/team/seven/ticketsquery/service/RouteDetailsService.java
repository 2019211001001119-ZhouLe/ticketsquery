package team.seven.ticketsquery.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.seven.ticketsquery.domain.RouteDetails;
import team.seven.ticketsquery.mapper.RouteDetailsMapper;
import team.seven.ticketsquery.vo.RouteDetailsVo;

import java.util.List;


/**
 * @description:
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@Service
public class RouteDetailsService extends ServiceImpl<RouteDetailsMapper, RouteDetails> {
    @Autowired
    RouteDetailsMapper mapper;
    public List<RouteDetails> findDetailsByRouteID(String routertrainId) {
        QueryWrapper<RouteDetails> routeDetailsQueryWrapper = new QueryWrapper<>();
        routeDetailsQueryWrapper.eq("routertrain_id", routertrainId);
        return mapper.findDetailsByRouteID(routertrainId);
    }
    public RouteDetails getOneByTIdAndSId(String routertrainId, String trainstationId) {
        QueryWrapper<RouteDetails> routeDetailsQueryWrapper = new QueryWrapper<>();
        routeDetailsQueryWrapper
                .eq("routertrain_id", routertrainId)
                .eq("trainstation_id", trainstationId);
        return getOne(routeDetailsQueryWrapper);
    }


    //新闻管理模块--发布列车停靠站的相关信息
    public Page<RouteDetailsVo> RouteDetailsList(Page page) {
        return mapper.queryRouteDetailsList(page);
    }

    //新闻管理模块 -- 通过车次号查询
    public Page<RouteDetailsVo> queryAllByRoutertrainId(Page page, @Param(value = "routertrain_id") String routertrainId){
        if(routertrainId==null){

        }
        return mapper.queryAllByRoutertrainId(page,routertrainId);
    }
}
