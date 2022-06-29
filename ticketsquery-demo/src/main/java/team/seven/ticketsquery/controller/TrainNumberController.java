package team.seven.ticketsquery.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.domain.TrainNumber;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.service.TrainNumberService;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    //
    @RequestMapping(value = "/train_number/{train_number_id}", method = RequestMethod.GET)
    ResultVO<?> trainNumberById(@PathVariable String train_number_id) {
        return new ResultVO<>(service.getById(train_number_id));
    }
    //根据出发地和目的地模糊查询符合条件的车次
    //例如 成都 武汉 会查出G2963和Z334这两条数据
    //分别是 成都东 到  深圳北
    //和    成都   到  深圳
    @RequestMapping(value = "/train_number/{departureStationId}/{arrivalStationId}", method = RequestMethod.GET)
    ResultVO<?> queryTrainNumberByDepartureAndArrival(@PathVariable String departureStationId, @PathVariable String arrivalStationId,
                                                      @RequestParam(value = "departureTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date departureTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(departureTime.getTime());
        List<TrainNumber> tnList = service.TrainNumberByDepartAndArrive(departureStationId, arrivalStationId, date);
        return new ResultVO<>(tnList);
    }

    //所有车次信息
    @RequestMapping(value = "/train_number", method = RequestMethod.GET)
    ResultVO<?> allTrainNumber() {
        return new ResultVO<>(service.list());
    }

    //车次所属城市名
    @RequestMapping(value = "/train_number/city", method = RequestMethod.GET)
    ResultVO<?> allTrainNumberCity() {
        return new ResultVO<>(service.cityName());
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
        if(!trainNumber.getRoutertrainId().equals(trainNumberId))
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
    ResultVO<?> trainNumberPage(@RequestParam(value = "current", required = false) int current, @RequestParam(value = "size", required = false) int size) {
        Page<TrainNumber> trainNumberPage = new Page<>(current, size);
        Page<TrainNumber> page = new LambdaQueryChainWrapper<>(service.getBaseMapper()).page(trainNumberPage);
        return new ResultVO<>(page);
    }


}
