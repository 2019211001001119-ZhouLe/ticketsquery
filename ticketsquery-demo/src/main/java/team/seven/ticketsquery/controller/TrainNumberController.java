package team.seven.ticketsquery.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    //根据出发地和目的地模糊查询符合条件的车次
    //例如 成都 武汉 会查出G2963和Z334这两条数据
    //分别是 成都东 到  深圳北
    //和    成都   到  深圳
    @RequestMapping("/train_number/{departureStationId}/{arrivalStationId}")
    ResultVO<?> queryTrainNumberByDepartureAndArrival(@PathVariable String departureStationId, @PathVariable String arrivalStationId) {
        List<TrainNumber> tnList = service.TrainNumberByDepartAndArrive(departureStationId, arrivalStationId);
        return new ResultVO<>(tnList);
    }

    //所有车次信息
    @RequestMapping(value = "/train_number", method = RequestMethod.GET)
    ResultVO<?> allTrainNumber() {
        return new ResultVO<>(service.list());
    }

    //新增车次信息
    //返回code为201
    @RequestMapping(value = "/train_number", method = RequestMethod.POST)
    ResultVO<?> addTrainNumber(@RequestBody TrainNumber trainNumber) {
        return new ResultVO<>(service.save(trainNumber) ? ResultStatusEnum.CREATE_SUCCESS : ResultStatusEnum.BAD_REQUEST);
    }

    //更新车次信息
    @RequestMapping(value = "/train_number/{trainNumberId}", method = RequestMethod.PUT)
    ResultVO<?> updTrainNumber(@RequestBody TrainNumber trainNumber, @PathVariable String trainNumberId) throws Exception {
        if(!trainNumber.getTrainId().equals(trainNumberId))
            throw new Exception();
        return new ResultVO<>(service.updateById(trainNumber) ? ResultStatusEnum.SUCCESS : ResultStatusEnum.BAD_REQUEST);
    }

    //删除车次信息
    //返回code为204
    @RequestMapping(value = "/train_number/{trainNumberId}", method = RequestMethod.DELETE)
    ResultVO<?> delTrainNumber(@PathVariable String trainNumberId) {
        return new ResultVO<>(service.removeById(trainNumberId) ? ResultStatusEnum.DELETE_SUCCESS : ResultStatusEnum.BAD_REQUEST);
    }

    //分页查询
    @RequestMapping(value = "/train_number_page", method = RequestMethod.GET)
    ResultVO<?> trainNumberPage(@RequestParam int current, @RequestParam int size) {
        Page<TrainNumber> trainNumberPage = new Page<>(current, size);
        Page<TrainNumber> page = new LambdaQueryChainWrapper<>(service.getBaseMapper()).page(trainNumberPage);
        return new ResultVO<>(page);
    }


}
