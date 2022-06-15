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
@CrossOrigin
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
        return new ResultVO<>(
                service.save(train) ? ResultStatusEnum.CREATE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    @RequestMapping(value = "/train/{trainId}", method = RequestMethod.DELETE)
    ResultVO<?> delTrain(@PathVariable String trainId) {
        return new ResultVO<>(
                service.removeById(trainId) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    @RequestMapping(value = "/train/{trainId}", method = RequestMethod.PUT)
    ResultVO<?> updTrain(@RequestBody Train train, @PathVariable String trainId) {
        if(!train.getTrainId().equals(trainId)) {
            return new ResultVO<>(ResultStatusEnum.UNAUTHORIZED);
        }
        return new ResultVO<>(
                service.updateById(train) ? ResultStatusEnum.SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }
}
