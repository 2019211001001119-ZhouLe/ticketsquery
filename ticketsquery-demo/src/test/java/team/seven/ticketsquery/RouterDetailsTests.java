package team.seven.ticketsquery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.seven.ticketsquery.domain.RouteDetails;
import team.seven.ticketsquery.service.RouteDetailsService;

import java.util.List;

/**
 * @description: 经停信息测试
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@SpringBootTest
public class RouterDetailsTests {
    @Autowired
    RouteDetailsService service;

    @Test
    void listTest() {
        List<RouteDetails> l = service.getRouteDetailsList();
    }
}
