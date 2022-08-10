package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentBackDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private  Integer userId;

    private  String avatar;

    private  String nickname;

    private  Integer replyId;

    private  String replyNickname;

    private  Integer articleId;

    private  String articleTitle;

    private Integer likeCount;

    private  String commentContent;

    private  String createTime;

}
