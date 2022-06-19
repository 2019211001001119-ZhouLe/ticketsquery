package team.seven.ticketsquery.service;


/*
 * @author: lew
 * @date:  2022/6/13
 * @version: 1.8
 * @description:车站业务层
 */


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.seven.ticketsquery.domain.TrainStation;
import team.seven.ticketsquery.mapper.TrainStationMapper;
import team.seven.ticketsquery.vo.TrainStationVo;

import java.util.List;

@Service
public class TrainStationService extends ServiceImpl<TrainStationMapper, TrainStation> {

    @Autowired
    TrainStationMapper trainStationMapper;
    //查询所有车站
    public List<TrainStationVo> trainStationList() {
        return trainStationMapper.queryTrainStationList();
    }

    //根据车站名称来查询
    public List<TrainStationVo> queryByTrainStationName(String trainstationName) {
        return trainStationMapper.queryByTrainstationName(trainstationName);
    }


}