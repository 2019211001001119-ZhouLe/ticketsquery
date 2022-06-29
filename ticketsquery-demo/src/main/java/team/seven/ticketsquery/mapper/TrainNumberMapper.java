package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.format.annotation.DateTimeFormat;
import team.seven.ticketsquery.domain.TrainNumber;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description: 车次mapper
 * @author: ZhouLe
 * @create: 2022-06-17
 * @version: 1.0
 */
@Mapper
public interface TrainNumberMapper extends BaseMapper<TrainNumber> {
    @Select("SELECT routertrain_id, train_id, routertrain_type, " +
            "a.trainstation_name AS departure_station_id, " +
            "b.trainstation_name AS arrival_station_id, departure_time, arrival_time " +
            "from tb_routertrain, tb_trainstation AS a, tb_trainstation AS b " +
            "WHERE departure_station_id = a.trainstation_id " +
            "AND arrival_station_id = b.trainstation_id " +
            "AND a.trainstation_name LIKE '%${departureStationId}%' " +
            "AND b.trainstation_name LIKE '%${arrivalStationId}%'" +
            "AND departure_time LIKE '%${departureTime}%'")
    List<TrainNumber> findTrainNumberByDepartAndArrive(String departureStationId, String arrivalStationId ,@DateTimeFormat(pattern = "yyyy-MM-dd")String departureTime );

    @Select("SELECT r.*, c.city_name FROM tb_routertrain AS r, tb_city AS c, tb_trainstation AS ts " +
            "WHERE r.departure_station_id = ts.trainstation_id " +
            "AND ts.city_id = c.city_id")
    List<Map<String, String>> findCity();
}
