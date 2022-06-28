package team.seven.ticketsquery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.service.CityService;

/**
 * @description:
 * @author: ZhouLe
 * @create: 2022-06-28
 * @version: 1.0
 */

@CrossOrigin
@RestController
public class CityController {
    @Autowired
    CityService service;
    @GetMapping("/city")
    public ResultVO<?> allCity() {
        return new ResultVO<>(service.list());
    }
}
