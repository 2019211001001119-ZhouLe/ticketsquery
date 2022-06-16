package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.seven.ticketsquery.domain.News;
import team.seven.ticketsquery.domain.ResultVO;

/**
 * User: 谢礼治
 * Date: 2022/6/16
 * Time: 9:26
 * Description: No Description
 */

@Mapper
@Repository
public interface NewsMapper extends BaseMapper<News> {



}
