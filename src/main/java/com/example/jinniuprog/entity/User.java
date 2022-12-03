package com.example.jinniuprog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author luo
 * @since 2022-12-03
 */
@Getter
@Setter
  public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 系统用户id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 系统用户名
     */
      private String name;

      /**
     * 用户密码
     */
      private String password;

      /**
     * 该用户的电话号码
     */
      private String telephone;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
