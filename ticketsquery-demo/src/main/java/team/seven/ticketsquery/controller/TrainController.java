package team.seven.ticketsquery.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    //获取所有车辆信息
    @RequestMapping(value = "/train", method = RequestMethod.GET)
    ResultVO<?> getTrainList(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Train> trains;
        if (keyword!=null) {
            QueryWrapper<Train> trainQueryWrapper = new QueryWrapper<>();
            trainQueryWrapper.like("train_name", keyword);

            trains = service.list(trainQueryWrapper);
        } else {
            trains = service.list();
        }

        return new ResultVO<>(trains);
    }

    //新增车辆信息
    //返回code为201
    @RequestMapping(value = "/train", method = RequestMethod.POST)
    ResultVO<?> saveTrain(@RequestBody Train train) {
        return new ResultVO<>(
                service.save(train) ? ResultStatusEnum.CREATE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //删除车辆信息
    //返回code为204
    @RequestMapping(value = "/train/{trainId}", method = RequestMethod.DELETE)
    ResultVO<?> delTrain(@PathVariable String trainId) {
        return new ResultVO<>(
                service.removeById(trainId) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //更新车辆信息
    @RequestMapping(value = "/train/{trainId}", method = RequestMethod.PUT)
    ResultVO<?> updTrain(@RequestBody Train train, @PathVariable String trainId) {
        if(!train.getTrainId().equals(trainId)) {
            return new ResultVO<>(ResultStatusEnum.UNAUTHORIZED);
        }
        return new ResultVO<>(
                service.updateById(train) ? ResultStatusEnum.SUCCESS : ResultStatusEnum.NOT_FOUND
        );
    }

    //分页查询
    @RequestMapping(value = "/trainpage")
    ResultVO<?> trainPage(@RequestParam(value = "current", required = false) int current, @RequestParam(value = "size", required = false) int size) {
        Page<Train> trainPage = new Page<>(current, size);
        Page<Train> page = new LambdaQueryChainWrapper<>(service.getBaseMapper()).page(trainPage);
        return new ResultVO<>(page);
    }
}
