package com.nowcoder.community.service;

//import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
//import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService implements CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

//    @Autowired
//    private LoginTicketMapper loginTicketMapper;

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    //2
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        //
        if (user == null) {
            throw new IllegalArgumentException("��������Ϊ��!");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "�˺Ų���Ϊ��");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "���벻��Ϊ��!");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "���䲻��Ϊ��!");
            return map;
        }

        // ��֤�˺�
        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg", "���˺��Ѵ���!");
            return map;
        }

        // ��֤����
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null) {
            map.put("emailMsg", "email has been register!");
            return map;
        }

        // ע���û�
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        // http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "active", content);

        return map;
    }

//3              ����id�ͼ�����  101/code

    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

//    5 ����½
    @Autowired
    LoginTicketMapper loginTicketMapper;
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();
        //
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "num cannot null!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "password cannot null!");
            return map;
        }

        //
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "num not exit!");
            return map;
        }

        //
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "num not active!");
            return map;
        }

        //
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "password not true!");
            return map;
        }

        //
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }
//5 �˳�
    public void logout(String ticket) {
        loginTicketMapper.updateStatus(ticket, 1);
    }


//    6 ��������д����cookie��ƾ֤
    public  LoginTicket findLoginTicket(String ticket){
        return  loginTicketMapper.selectByTicket(ticket);
    }


//    public LoginTicket findLoginTicket(String ticket) {
//        return loginTicketMapper.selectByTicket(ticket);
//    }
//

    public int updateHeader(int userId, String headerUrl) {
        return userMapper.updateHeader(userId, headerUrl);
    }
//
//}
}