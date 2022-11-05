package com.zhao.utils;

import com.mysql.cj.util.StringUtils;
import com.zhao.api.RedisService;
import com.zhao.exception.ServiceException;
import com.zhao.pojo.User;
import com.zhao.vo.PasswordVO;
import com.zhao.vo.UserRegisterVO;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zhao.constant.RedisPrefixConst.USER_EMAIL_KEY;
import static com.zhao.enums.StatusCodeEnum.FAIL;

@Component
@SuppressWarnings("all")
public class CommonUtil {


    /**
     * 检测邮箱是否合法
     * @param username 用户名
     * @return 合法状态
     */
    public static boolean checkEmail(String username) {
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(username);
        //进行正则匹配
        return m.matches();
    }

    public static String getRandomCode() {
        // 生成六位随机验证码发送
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(9) + 1);
        }
        return str.toString();
    }

    public static String confirmEmailCode(UserRegisterVO userRegisterVO, RedisService redisService) {
        String email = userRegisterVO.getEmail();
        String storeEmail = (String) redisService.get(USER_EMAIL_KEY + email);
        if (StringUtils.isNullOrEmpty(storeEmail)) {
            throw new ServiceException(FAIL.getCode(),"验证码已过期");
        }
        if (!userRegisterVO.getCode().equals(storeEmail)) {
            throw new ServiceException(FAIL.getCode(),"验证码错误");
        }
        return email;
    }

    public static void ConfirmPasswordBackInfo(PasswordVO passwordVO, User loginUser) {
        if (passwordVO.getNewPassword().equals(passwordVO.getOldPassword())) {
            throw new ServiceException(FAIL.getCode(), FAIL.getDesc());
        }
        if (!passwordVO.getNewPassword().equals(passwordVO.getConfirmPassword())) {
            throw new ServiceException(FAIL.getCode(), FAIL.getDesc());
        }
        if (!BCrypt.checkpw(passwordVO.getOldPassword(), loginUser.getPassword())) {
            throw new ServiceException(FAIL.getCode(), FAIL.getDesc());
        }
    }
}
