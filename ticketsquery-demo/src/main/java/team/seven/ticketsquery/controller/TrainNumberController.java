package team.seven.ticketsquery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.domain.TrainNumber;
import team.seven.ticketsquery.service.TrainNumberService;

import java.util.List;
import java.util.Map;

/**
 * @description: 车次controller
 * @author: ZhouLe
 * @create: 2022-06-17
 * @version: 1.0
 */
@CrossOrigin
@RestController
public class TrainNumberController {
    @Autowired
    TrainNumberService service;
    @RequestMapping("/train_number/{departureStationId}/{arrivalStationId}")
    ResultVO<?> queryTrainNumberByDepartureAndArrival(@PathVariable String departureStationId, @PathVariable String arrivalStationId) {
        List<TrainNumber> tnList = service.TrainNumberByDepartAndArrive(departureStationId, arrivalStationId);
        return new ResultVO<>(tnList);
    }
}
