package com.zhao.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UserLogin对象", description="用户登录记录")
@TableName("tb_user_login")
@Accessors(chain = true)
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer userLoginId;

    private String nickname;

    private String ipAddress;

    private String ipSources;

    private String loginType;

    private Date loginTime;

    private Date lastLoginTime;

    private String avatar;

    private boolean status;// 上线，下线

}
