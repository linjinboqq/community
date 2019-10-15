package com.nowcoder.community.quartz;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.ElasticsearchService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.RedisKeyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostScoreRefreshJob implements Job, CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(PostScoreRefreshJob.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    // ţ�ͼ�Ԫ
    private static final Date epoch;

    static {
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException("��ʼ��ţ�ͼ�Ԫʧ��!", e);
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getPostScoreKey();
        BoundSetOperations operations = redisTemplate.boundSetOps(redisKey);

        if (operations.size() == 0) {
            logger.info("[����ȡ��] û����Ҫˢ�µ�����!");
            return;
        }

        logger.info("[����ʼ] ����ˢ�����ӷ���: " + operations.size());
        while (operations.size() > 0) {
            this.refresh((Integer) operations.pop());
        }
        logger.info("[�������] ���ӷ���ˢ�����!");
    }

    private void refresh(int postId) {
        DiscussPost post = discussPostService.findDiscussPostById(postId);

        if (post == null) {
            logger.error("�����Ӳ�����: id = " + postId);
            return;
        }

        // �Ƿ񾫻�
        boolean wonderful = post.getStatus() == 1;
        // ��������
        int commentCount = post.getCommentCount();
        // ��������
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, postId);

        // ����Ȩ��
        double w = (wonderful ? 75 : 0) + commentCount * 10 + likeCount * 2;
        // ���� = ����Ȩ�� + ��������
        double score = Math.log10(Math.max(w, 1))
                + (post.getCreateTime().getTime() - epoch.getTime()) / (1000 * 3600 * 24);
        // �������ӷ���
        discussPostService.updateScore(postId, score);
        // ͬ����������
        post.setScore(score);
        elasticsearchService.saveDiscussPost(post);
    }
}
