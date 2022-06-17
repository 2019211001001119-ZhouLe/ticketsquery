package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import team.seven.ticketsquery.domain.RouteDetails;

import java.util.List;

/**
 * @description: 经停信息mapper
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@Mapper
public interface RouteDetailsMapper extends BaseMapper<RouteDetails> {
    @Select("SELECT r.routerdetail_id, r.routertrain_id, t.trainstation_name AS trainstation_id, r.arrival_time, departure_time \n" +
            "FROM tb_routerdetail as r,tb_trainstation AS t \n" +
            "WHERE routertrain_id = #{routeID, jdbcType=VARCHAR}\n" +
            "AND r.trainstation_id = t.trainstation_id")
    List<RouteDetails> findDetailsByRouteID(String routeId);
}
