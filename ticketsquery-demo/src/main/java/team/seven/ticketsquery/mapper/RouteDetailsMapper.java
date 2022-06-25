package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.seven.ticketsquery.domain.RouteDetails;
import team.seven.ticketsquery.vo.RouteDetailsVo;

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

    //联表查询车次信息
    @Select("SELECT r.*,t.trainstation_name,t.trainstation_id,a.admin_id,a.admin_name from " +
            "tb_routerdetail r, tb_trainstation t,tb_administrator a " +
            "where r.trainstation_id = t.trainstation_id and a.admin_id = r.admin_id")
    Page<RouteDetailsVo> queryRouteDetailsList(Page page);

    //联表条件查询
    @Select("SELECT r.*,t.trainstation_name,t.trainstation_id,a.admin_id,a.admin_name from " +
            "tb_routerdetail r, tb_trainstation t,tb_administrator a " +
            "where r.trainstation_id = t.trainstation_id and a.admin_id = r.admin_id and routertrain_id = #{routertrain_id} ")
    Page<RouteDetailsVo> queryAllByRoutertrainId(Page page,@Param(value = "routertrain_id") String routertrainId);
}
