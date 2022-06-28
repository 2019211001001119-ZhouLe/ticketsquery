package team.seven.ticketsquery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
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

    @TableId(type = IdType.AUTO)
    private Integer newsId;
    @NotNull(message = "必须添加发布者")
    private String adminId;
    @Length(min=3, max=25,message = "新闻标题长度必须在3-25之间")
    private String newsTitle;
    private Date newsPublishTime;
    private String newsUrl;
}
