package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import team.seven.ticketsquery.domain.Train;

/**
 * @description: 列车Mapper
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@Mapper
public interface TrainMapper extends BaseMapper<Train> {
}
