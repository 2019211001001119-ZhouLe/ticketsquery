package team.seven.ticketsquery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.Train;
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
    List<Train> getTrainList() {
        return service.list();
    }

    @RequestMapping(value = "/train", method = RequestMethod.POST)
    void saveTrain(@RequestBody Train train) {
        service.save(train);
    }

    @RequestMapping(value = "/train/{trainId}", method = RequestMethod.DELETE)
    void delTrain(@PathVariable String trainId)
    {
        service.removeById(trainId);
    }

    @RequestMapping(value = "/train/{trainId}", method = RequestMethod.PUT)
    void updTrain(@RequestBody Train train) {
        service.update(train, null);
    }
}
