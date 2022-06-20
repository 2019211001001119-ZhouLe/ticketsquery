package team.seven.ticketsquery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.domain.TrainNumber;
import team.seven.ticketsquery.enums.ResultStatusEnum;
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

    @RequestMapping(value = "/train_number", method = RequestMethod.GET)
    ResultVO<?> allTrainNumber() {
        return new ResultVO<>(service.list());
    }

    @RequestMapping(value = "/train_number", method = RequestMethod.POST)
    ResultVO<?> addTrainNumber(@RequestBody TrainNumber trainNumber) {
        return new ResultVO<>(service.save(trainNumber) ? ResultStatusEnum.SUCCESS : ResultStatusEnum.BAD_REQUEST);
    }

    @RequestMapping(value = "/train_number/{trainNumberId}", method = RequestMethod.PUT)
    ResultVO<?> updTrainNumber(@RequestBody TrainNumber trainNumber, @PathVariable String trainNumberId) throws Exception {
        if(!trainNumber.getTrainId().equals(trainNumberId))
            throw new Exception();
        return new ResultVO<>(service.updateById(trainNumber) ? ResultStatusEnum.SUCCESS : ResultStatusEnum.BAD_REQUEST);
    }

    @RequestMapping(value = "/train_number/{trainNumberId}", method = RequestMethod.DELETE)
    ResultVO<?> delTrainNumber(@PathVariable String trainNumberId) {
        return new ResultVO<>(service.removeById(trainNumberId) ? ResultStatusEnum.SUCCESS : ResultStatusEnum.BAD_REQUEST);
    }


}
