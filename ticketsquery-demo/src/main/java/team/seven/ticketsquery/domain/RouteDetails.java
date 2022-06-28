package team.seven.ticketsquery.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Integer routerdetailId;
    private String trainstationId;
    private String routertrainId;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date arrivalTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date departureTime;
    private int routerdetailStatus;
    @JsonFormat(pattern = "hh:mm:ss", timezone = "UTC+8")
    @DateTimeFormat(pattern = "hh:mm:ss")
    private Date laterTime;
    private String adminId;
}
