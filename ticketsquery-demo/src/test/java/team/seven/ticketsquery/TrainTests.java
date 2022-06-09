package team.seven.ticketsquery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.seven.ticketsquery.service.TrainService;

/**
 * @description: 列车测试
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@SpringBootTest
public class TrainTests {
    @Autowired
    TrainService service;

    @Test
    void listTest() {
        service.list();
    }
}
