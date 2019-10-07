package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    // ��ѯ��ǰ�û��ĻỰ�б�,���ÿ���Ựֻ����һ�����µ�˽��.
    List<Message> selectConversations(int userId, int offset, int limit);

    // ��ѯ��ǰ�û��ĻỰ����.
    int selectConversationCount(int userId);

    // ��ѯĳ���Ự��������˽���б�.
    List<Message> selectLetters(String conversationId, int offset, int limit);

    // ��ѯĳ���Ự��������˽������.
    int selectLetterCount(String conversationId);

    // ��ѯδ��˽�ŵ�����
    int selectLetterUnreadCount(int userId, String conversationId);

    // ������Ϣ
    int insertMessage(Message message);

    // �޸���Ϣ��״̬
    int updateStatus(List<Integer> ids, int status);

}
