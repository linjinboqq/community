package com.nowcoder.community;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.service.DiscussPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CaffeineTests {

    @Autowired
    private DiscussPostService postService;

    @Test
    public void initDataForTest() {
        for (int i = 0; i < 100000; i++) {
            DiscussPost post = new DiscussPost();
            post.setUserId(111);
            post.setTitle("��������ְů���ƻ�");
            post.setContent("����ľ�ҵ���ƣ�ȷʵ�����ֹۡ����˸��꣬�·���ˮһ�㣬" +
                    "���������������Ұ��19�����û��Ҫ���𣿣�18�챻�Ż����û�г�·���𣿣�" +
                    "��ҵġ��������롰����������ǣ����ÿ��Ǳ������������ţ��С���С����ǵ��ģ�" +
                    "����ţ�;�������ʱ��Ϊ�������ʲô�ˣ�Ϊ�˰�����Ҷȹ�����������ţ�����ر�����60+����ҵ��" +
                    "������������ְů���ƻ�������18��&19�죬����0 offer��");
            post.setCreateTime(new Date());
            post.setScore(Math.random() * 2000);
            postService.addDiscussPost(post);
        }
    }

    @Test
    public void testCache() {
        System.out.println(postService.findDiscussPosts(0, 0, 10, 1));
        System.out.println(postService.findDiscussPosts(0, 0, 10, 1));
        System.out.println(postService.findDiscussPosts(0, 0, 10, 1));
        System.out.println(postService.findDiscussPosts(0, 0, 10, 0));
    }

}
