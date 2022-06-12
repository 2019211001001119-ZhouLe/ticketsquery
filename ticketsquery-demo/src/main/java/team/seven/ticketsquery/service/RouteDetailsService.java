package team.seven.ticketsquery.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.seven.ticketsquery.domain.RouteDetails;
import team.seven.ticketsquery.mapper.RouteDetailsMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;


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
    public List<Map<String, Object>> trainNameList(String trainName) {
        return mapper.findTrainStationNameByRouteID(trainName);
    }

    public boolean setLateTime(RouteDetails details, Date lateTime) {
        details.setLaterTime(lateTime);
        return updateById(details);
    }

}
