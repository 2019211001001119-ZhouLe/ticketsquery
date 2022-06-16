package team.seven.ticketsquery.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;

/**
 * User: 谢礼治
 * Date: 2022/6/16
 * Time: 9:18
 * Description: No Description
 */

@Data
@TableName("tb_news")
public class News {

    @TableId
    private Integer newsId;
    private String adminId;
    private String newsTitle;
    private Date newsPublishTime;
    private String newsUrl;
}
