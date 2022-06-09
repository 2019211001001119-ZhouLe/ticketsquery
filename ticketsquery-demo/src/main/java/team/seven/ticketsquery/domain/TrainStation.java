package team.seven.ticketsquery.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @version 1.8
 * @date 2022/6/8 15:44
 */

@Data
@TableName("tb_trainstation")
public class TrainStation {
    @TableId
    private String trainstationId;
    private Integer cityId;
    private String trainstationName;
}
