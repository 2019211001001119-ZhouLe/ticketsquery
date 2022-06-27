package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import team.seven.ticketsquery.domain.RouteDetails;
import team.seven.ticketsquery.vo.RouteDetailsVo;

import java.util.Date;
import java.util.List;

/**
 * @description: 经停信息mapper
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@Mapper
public interface RouteDetailsMapper extends BaseMapper<RouteDetails> {
    @Select("SELECT r.routerdetail_id, r.routertrain_id, t.trainstation_name AS trainstation_id, r.arrival_time, r.departure_time, r.later_time \n" +
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


    @Update("update tb_routerdetail " +
            "SET later_time = #{laterTime} " +
            "WHERE routerdetail_id = #{routerdetailId} " +
            "AND routertrain_id = #{routertrainId}")
    int lateRouteDetail(@Param(value = "laterTime")Date laterTime, @Param(value = "routerdetailId") int routerdetailId, @Param(value = "routertrainId") String routertrainId);

    @Update("update tb_routerdetail " +
            "SET later_time = #{detail.laterTime}," +
            "trainstation_id = #{detail.trainstationId}," +
            "arrival_time = #{detail.arrivalTime}," +
            "departure_time = #{detail.departureTime}," +
            "routerdetail_status = #{detail.routerdetailStatus} " +
            "WHERE routerdetail_id = #{detail.routerdetailId} " +
            "AND routertrain_id = #{detail.routertrainId}")
    int updateRouteDetail(@Param(value = "detail") RouteDetails routeDetails);

    @Delete("delete from tb_routerdetail " +
            "WHERE routerdetail_id = #{routerdetailId} " +
            "AND routertrain_id = #{routertrainId}")
    int deleteRouteDetail(@Param(value = "routerdetailId") int routerdetailId, @Param(value = "routertrainId") String routertrainId);
}
