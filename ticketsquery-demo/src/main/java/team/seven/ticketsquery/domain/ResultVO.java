package team.seven.ticketsquery.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import team.seven.ticketsquery.enums.ResultStatusEnum;

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

}
