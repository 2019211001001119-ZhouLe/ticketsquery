package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import team.seven.ticketsquery.domain.TrainStation;
import org.apache.ibatis.annotations.Mapper;
import team.seven.ticketsquery.vo.TrainStationVo;

import java.util.List;


@Mapper
public interface TrainStationMapper extends BaseMapper<TrainStation> {

    //查询所有车站信息
    @Select("SELECT t.*,p.province_name,c.city_name FROM tb_trainstation t ,tb_city c,tb_province p" +
            " WHERE  t.city_id = c.city_id and c.province_id = p.province_id ")
    List<TrainStationVo> queryTrainStationList();

    //根据车站名关键字查询
    @Select("SELECT t.*,p.province_name,c.city_name FROM tb_trainstation t ,tb_city c,tb_province p" +
            " WHERE  t.city_id = c.city_id and c.province_id = p.province_id " +
            "and trainstation_name like CONCAT( '%',#{trainstation_name} ,'%')")
    List<TrainStationVo> queryByTrainstationName(String trainstationName);

}
