package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.UserDTO;
import com.zhao.dto.UserSignalDTO;
import com.zhao.pojo.User;
import com.zhao.vo.UserQueryVO;
import com.zhao.vo.UserRegisterVO;

import java.util.List;

public interface UserService extends IService<User> {

    List<String> getUserRolesByUserId(Integer id);

    List<UserDTO> getUserList( UserQueryVO userQueryVO);

    int updateUserById( UserDTO userDTO);

    int addUser(UserDTO userDTO);

    int updateSilenceById(boolean isSilence, Integer userId);

    List<UserSignalDTO> getUserRoleList(Integer current, Integer size, String nickname);

    int updateUserRole(UserSignalDTO userSignalDTO);

    void sendCode(String email);

    void registerUser(UserRegisterVO userRegisterVO);

    void forgetPassword(UserRegisterVO userRegisterVO);
}
