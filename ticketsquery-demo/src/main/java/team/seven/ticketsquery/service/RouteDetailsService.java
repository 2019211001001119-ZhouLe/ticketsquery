package team.seven.ticketsquery.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.seven.ticketsquery.domain.RouteDetails;
import team.seven.ticketsquery.mapper.RouteDetailsMapper;

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
    public List<RouteDetails> findDetailsByRouteID(String trainName) {
        return mapper.findDetailsByRouteID(trainName);
    }
    public RouteDetails getOneByTIdAndSId(String routertrainId, String trainstationId) {
        QueryWrapper<RouteDetails> routeDetailsQueryWrapper = new QueryWrapper<>();
        routeDetailsQueryWrapper
                .eq("routertrain_id", routertrainId)
                .eq("trainstation_id", trainstationId);
        return getOne(routeDetailsQueryWrapper);
    }

}
