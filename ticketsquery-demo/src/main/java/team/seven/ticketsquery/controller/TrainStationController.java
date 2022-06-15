package team.seven.ticketsquery.controller;


/*
 * @author: lew
 * @date:  2022/6/13
 * @version: 1.8
 * @description:车站控制层
 */

import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.domain.TrainStation;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.service.TrainStationService;

import java.util.List;

@RestController
public class TrainStationController {
    private TrainStationService trainStationService;
    TrainStationController(TrainStationService trainStationService) {
        this.trainStationService = trainStationService;
    }

    @RequestMapping(value = "/trainstation", method = RequestMethod.GET)
    ResultVO<?> getTrainStationList() {
        List<TrainStation> trainStationList = trainStationService.list();
        return new ResultVO<>(trainStationList);
    }

    @RequestMapping(value = "/trainstation", method = RequestMethod.POST)
    ResultVO<?> saveTrainStation(@RequestBody TrainStation trainStation) {
        return new ResultVO<>(
                trainStationService.save(trainStation) ? ResultStatusEnum.CREATE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    @RequestMapping(value = "/trainstation/{trainstationId}", method = RequestMethod.DELETE)
    ResultVO<?> delTrainStation(@PathVariable String trainstationId) {
        return new ResultVO<>(
                trainStationService.removeById(trainstationId) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    @RequestMapping(value = "/trainstation/{trainstationId}", method = RequestMethod.PUT)
    ResultVO<?> updTrainStation(@RequestBody TrainStation trainStation, @PathVariable String trainstationId) {
        if(!trainStation.getTrainstationId().equals(trainstationId)) {
            return new ResultVO<>(ResultStatusEnum.UNAUTHORIZED);
        }
        return new ResultVO<>(
                trainStationService.updateById(trainStation) ? ResultStatusEnum.SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }
}