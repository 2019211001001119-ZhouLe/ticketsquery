package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import team.seven.ticketsquery.domain.City;

/**
 * @description: city的mapper
 * @author: ZhouLe
 * @create: 2022-06-28
 * @version: 1.0
 */
@Mapper
public interface CityMapper extends BaseMapper<City> {
}
