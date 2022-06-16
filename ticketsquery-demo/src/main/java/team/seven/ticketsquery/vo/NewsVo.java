package team.seven.ticketsquery.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * User: 谢礼治
 * Date: 2022/6/16
 * Time: 9:24
 * Description: No Description
 */
@Data
public class NewsVo {

    private Integer newsId;
    private String adminId;
    private String newsTitle;
    private Date newsPublishTime;
    private String newsUrl;
}
