package com.benson.mediaapi.service.users;


import com.benson.mediaapi.model.User;
import com.benson.mediaapi.vo.AuthReqVO;

public interface UserService {
    String login(AuthReqVO authReqVO);

    Boolean register(User user);
}
