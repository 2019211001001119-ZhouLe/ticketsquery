package team.seven.ticketsquery.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
 * @description: 车次经停信息实体类
 * @author: ZhouLe
 * @create: 2022-06-09
 * @version: 1.0
 */
@Data
@TableName("tb_routerdetail")
public class RouteDetails {
    @TableId
    private int routerdetailId;
    private String trainstationId;
    private String routertrainId;
    private Date arrivalTime;
    private Date departureTime;
    private int routerdetailStatus;
    private Date laterTime;
}
