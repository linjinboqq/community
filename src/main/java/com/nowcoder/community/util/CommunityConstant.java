package com.nowcoder.community.util;

public interface CommunityConstant {

    /**
     * ����ɹ�
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * �ظ�����
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * ����ʧ��
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * Ĭ��״̬�ĵ�¼ƾ֤�ĳ�ʱʱ��
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * ��ס״̬�ĵ�¼ƾ֤��ʱʱ��
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * ʵ������: ����
     */
    int ENTITY_TYPE_POST = 1;

    /**
     * ʵ������: ����
     */
    int ENTITY_TYPE_COMMENT = 2;

    /**
     * ʵ������: �û�
     */
    int ENTITY_TYPE_USER = 3;
    /**
     * ����: ����
     */
    String TOPIC_COMMENT = "comment";

    /**
     * ����: ����
     */
    String TOPIC_LIKE = "like";

    /**
     * ����: ��ע
     */
    String TOPIC_FOLLOW = "follow";

    /**
     * ����: ����
     */
    String TOPIC_PUBLISH = "publish";

    /**
     * ����: ɾ��
     */
    String TOPIC_DELETE = "delete";

    /**
     * ϵͳ�û�ID
     */
    int SYSTEM_USER_ID = 1;

    /**
     * Ȩ��: ��ͨ�û�
     */
    String AUTHORITY_USER = "user";

    /**
     * Ȩ��: ����Ա
     */
    String AUTHORITY_ADMIN = "admin";

    /**
     * Ȩ��: ����
     */
    String AUTHORITY_MODERATOR = "moderator";


}
