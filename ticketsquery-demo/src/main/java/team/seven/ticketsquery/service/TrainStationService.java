package team.seven.ticketsquery.service;


/*
 * @author: lew
 * @date:  2022/6/13
 * @version: 1.8
 * @description:车站业务层
 */


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import team.seven.ticketsquery.domain.TrainStation;
import team.seven.ticketsquery.mapper.TrainStationMapper;


@Service
public class TrainStationService extends ServiceImpl<TrainStationMapper, TrainStation> {

}