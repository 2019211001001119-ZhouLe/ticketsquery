package team.seven.ticketsquery.controller;

import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.domain.Train;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.service.TrainService;

import java.util.List;

/**
 * @description: 列车Controller
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@RestController
public class TrainController {
    private final TrainService service;
    TrainController(TrainService service) {
        this.service = service;
    }

    @RequestMapping(value = "/train", method = RequestMethod.GET)
    ResultVO<?> getTrainList() {
        List<Train> trains = service.list();
        return new ResultVO<>(trains);
    }

    @RequestMapping(value = "/train", method = RequestMethod.POST)
    ResultVO<?> saveTrain(@RequestBody Train train) {
        service.save(train);
        return new ResultVO<>(ResultStatusEnum.CREATE_SUCCESS, train);
    }

    @RequestMapping(value = "/train/{trainId}", method = RequestMethod.DELETE)
    ResultVO<?> delTrain(@PathVariable String trainId)
    {
        service.removeById(trainId);
        return new ResultVO<>(ResultStatusEnum.DELETE_SUCCESS);
    }

    @RequestMapping(value = "/train/{trainId}", method = RequestMethod.PUT)
    ResultVO<?> updTrain(@RequestBody Train train, @PathVariable String trainId) {
        if(train.getTrainId().equals(trainId)) {
            if(service.updateById(train)) {
                return new ResultVO<>(train);
            }
            else {
                return new ResultVO<>(ResultStatusEnum.NOT_FOUND);
            }
        } else {
            return new ResultVO<>(ResultStatusEnum.UNAUTHORIZED);
        }
    }
}
