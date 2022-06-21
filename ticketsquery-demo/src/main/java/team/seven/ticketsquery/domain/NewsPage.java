package team.seven.ticketsquery.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * User: 谢礼治
 * Date: 2022/6/20
 * Time: 17:07
 * Description: No Description
 */
@Data
public class NewsPage {
    private String newsName;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date publishDate;
    private Integer pageNum;
    private Integer pageSize;
}
