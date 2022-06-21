package team.seven.ticketsquery.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import team.seven.ticketsquery.enums.ResultStatusEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 返回对象实体
 * @author: ZhouLe
 * @create: 2022-06-10
 * @version: 1.0
 */
@Getter
@Setter
public class ResultVO<T> {
    private Integer code;
    private String msg;
    private T data;
    public ResultVO() {
        this.code = ResultStatusEnum.SUCCESS.getCode();
        this.msg = ResultStatusEnum.SUCCESS.getMsg();
    }

    public ResultVO(T data) {
        this.code = ResultStatusEnum.SUCCESS.getCode();
        this.msg = ResultStatusEnum.SUCCESS.getMsg();
        this.data = data;
    }

    public ResultVO(ResultStatusEnum resultStatusEnum){
        this.code = resultStatusEnum.getCode();
        this.msg = resultStatusEnum.getMsg();
    }

    public ResultVO(ResultStatusEnum resultStatusEnum, T data){
        this.code = resultStatusEnum.getCode();
        this.msg = resultStatusEnum.getMsg();
        this.data =data;
    }

    /**
     * 输出json 响应信息
     * @param response
     * @throws IOException
     */
    public void out(HttpServletResponse response)throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(this));
    }
}
