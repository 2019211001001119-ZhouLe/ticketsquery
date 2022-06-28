package team.seven.ticketsquery.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description: city实体类
 * @author: ZhouLe
 * @create: 2022-06-28
 * @version: 1.0
 */
@Data
@TableName("tb_city")
public class City {
    @TableId
    Integer cityId;
    Integer provinceId;
    String cityName;
}
