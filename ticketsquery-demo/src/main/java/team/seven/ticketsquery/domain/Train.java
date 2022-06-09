package team.seven.ticketsquery.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description: 列车实体类
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@TableName("tb_train")
@Data
public class Train {
    @TableId
    private String trainId;
    private String trainName;
    private String trainSpeed;
}
