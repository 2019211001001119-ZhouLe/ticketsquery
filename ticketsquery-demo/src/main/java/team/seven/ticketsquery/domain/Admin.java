package team.seven.ticketsquery.domain;

import lombok.Data;

@Data
public class Admin {
    private String adminId;
    private String adminPwd;
    private String adminName;
    private Integer permission;
}
