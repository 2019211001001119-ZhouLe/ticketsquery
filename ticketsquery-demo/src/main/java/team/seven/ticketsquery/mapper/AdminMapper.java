package team.seven.ticketsquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.seven.ticketsquery.domain.Admin;

/**
 * User: 谢礼治
 * Date: 2022/6/13
 * Time: 9:03
 * Description: No Description
 */
@Mapper
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
}
