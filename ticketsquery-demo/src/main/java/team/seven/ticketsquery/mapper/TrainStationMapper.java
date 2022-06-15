package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import team.seven.ticketsquery.domain.TrainStation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface TrainStationMapper extends BaseMapper<TrainStation> {
    @Select("SELECT c.city_name,t.* FROM tb_trainstation t ,tb_city c WHERE  t.city_id = c.city_id")
    List<Map<String, Object>> queryTrainStationList();
}
