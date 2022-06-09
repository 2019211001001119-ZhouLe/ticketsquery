package team.seven.ticketsquery.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public List<RouteDetails> getRouteDetailsList() {
        return list();
    }
}
