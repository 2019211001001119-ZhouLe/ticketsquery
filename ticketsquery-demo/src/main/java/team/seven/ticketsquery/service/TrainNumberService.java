package team.seven.ticketsquery.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.seven.ticketsquery.domain.TrainNumber;
import team.seven.ticketsquery.mapper.TrainNumberMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 车次service
 * @author: ZhouLe
 * @create: 2022-06-17
 * @version: 1.0
 */
@Service
public class TrainNumberService extends ServiceImpl<TrainNumberMapper, TrainNumber> {
    @Autowired
    TrainNumberMapper mapper;
    public List<TrainNumber> TrainNumberByDepartAndArrive(String departureStationId, String arrivalStationId) {
        return mapper.findTrainNumberByDepartAndArrive(departureStationId, arrivalStationId);
    }

    public List<Map<String, String>> cityName() {

        return new ArrayList<>(mapper.findCity());
    }
}
