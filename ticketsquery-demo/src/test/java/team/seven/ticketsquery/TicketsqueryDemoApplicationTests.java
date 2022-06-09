package team.seven.ticketsquery;

import team.seven.ticketsquery.domain.TrainStation;
import team.seven.ticketsquery.mapper.TrainStationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TicketsqueryDemoApplicationTests {
    @Autowired
    TrainStationMapper trainStationMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void trainStationQueryTest() {
        List<TrainStation> trainStationList;
        trainStationList = trainStationMapper.selectList(null);
        assert (trainStationList.size() > 0);
    }
}
