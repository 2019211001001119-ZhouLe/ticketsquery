package team.seven.ticketsquery.controller;


/*
 * @author: lew
 * @date:  2022/6/13
 * @version: 1.8
 * @description:车站控制层
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.domain.TrainStation;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.service.TrainStationService;
import team.seven.ticketsquery.vo.TrainStationVo;


import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class TrainStationController {
    private TrainStationService trainStationService;
    TrainStationController(TrainStationService trainStationService) {
        this.trainStationService = trainStationService;
    }

    //查询所有车站
    @RequestMapping(value = "/trainstation", method = RequestMethod.GET)
    ResultVO<?> getTrainStationList() {
        List<TrainStationVo> trainStationList = trainStationService.trainStationList();
        return new ResultVO<>(trainStationList);
    }

    //根据车站名关键字查询
    @RequestMapping(value = "/trainstation/{trainstationName}", method = RequestMethod.GET)
    ResultVO<?> queryByTrainStationName(@PathVariable String trainstationName) {
            return new ResultVO<>(trainStationService.queryByTrainStationName(trainstationName));
    }

    //添加车站
    @RequestMapping(value = "/trainstation", method = RequestMethod.POST)
    ResultVO<?> saveTrainStation(@RequestBody TrainStation trainStation) {
        return new ResultVO<>(
                trainStationService.save(trainStation) ? ResultStatusEnum.CREATE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //删除车站
    @RequestMapping(value = "/trainstation/{trainstationId}", method = RequestMethod.DELETE)
    ResultVO<?> delTrainStation(@PathVariable String trainstationId) {
        return new ResultVO<>(
                trainStationService.removeById(trainstationId) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //更新车站信息
    @RequestMapping(value = "/trainstation/{trainstationId}", method = RequestMethod.PUT)
    ResultVO<?> updTrainStation(@RequestBody TrainStation trainStation, @PathVariable String trainstationId) {
        if(!trainStation.getTrainstationId().equals(trainstationId)) {
            return new ResultVO<>(ResultStatusEnum.UNAUTHORIZED);
        }
        return new ResultVO<>(
                trainStationService.updateById(trainStation) ? ResultStatusEnum.SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //批量删除车站
    @RequestMapping(value = "/trainstations/{trainstationIds}", method = RequestMethod.DELETE)
    ResultVO<?>  delTrainStations(@PathVariable String[] trainstationIds) {
        return new ResultVO<>(
                trainStationService.removeByIds(Arrays.asList(trainstationIds)) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }
}