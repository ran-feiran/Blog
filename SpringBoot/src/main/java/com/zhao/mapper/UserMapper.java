package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.UserDTO;
import com.zhao.dto.UserSignalDTO;
import com.zhao.pojo.User;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.UserQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户id获取当前用户角色
     * @param id
     * @return
     */
    List<String> getUserRolesByUserId(@Param("id") Integer id);

    /**
     * 查询用户列表
     * @param userQueryVO
     * @return
     */
    List<UserDTO> getUserList(@Param("condition") ConditionVO conditionVO);

    /**
     * 查询用户角色列表
     * @param current
     * @param size
     * @param nickname
     * @return
     */
    List<UserSignalDTO> getUserRoleList(@Param("current") Integer current,
                                        @Param("size") Integer size,
                                        @Param("nickname") String nickname);

    /**
     * 通过id修改用户
     * @param userDTO
     * @return
     */
    int updateUserById(@Param("userDTO") UserDTO userDTO);

    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    int addUser(@Param("userDTO") UserDTO userDTO);

    /**
     * 通过id修改用户禁言状态
     * @param isSilence
     * @param userId
     * @return
     */
    Integer updateSilenceById(@Param("isSilence") Integer isSilence,
                              @Param("userId") Integer userId);
}
