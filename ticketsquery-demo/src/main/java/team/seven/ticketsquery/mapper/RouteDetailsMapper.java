package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import team.seven.ticketsquery.domain.RouteDetails;

import java.util.List;
import java.util.Map;

/**
 * @description: 经停信息mapper
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@Mapper
public interface RouteDetailsMapper extends BaseMapper<RouteDetails> {
    @Select("SELECT trainstation_name " +
            "FROM tb_routerdetail ,tb_trainstation " +
            "WHERE routertrain_id = #{routeID, jdbcType=VARCHAR} " +
            "AND tb_routerdetail.trainstation_id = tb_trainstation.trainstation_id")
    List<Map<String, Object>> findTrainStationNameByRouteID(String routeId);
}
