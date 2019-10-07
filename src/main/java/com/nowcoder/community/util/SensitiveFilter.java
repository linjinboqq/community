package com.nowcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // �滻��
    private static final String REPLACEMENT = "***";

    // ���ڵ�
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                // ��ӵ�ǰ׺��
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            logger.error("�������д��ļ�ʧ��: " + e.getMessage());
        }
    }

    // ��һ�����д���ӵ�ǰ׺����
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null) {
                // ��ʼ���ӽڵ�
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            // ָ���ӽڵ�,������һ��ѭ��
            tempNode = subNode;

            // ���ý�����ʶ
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * �������д�
     *
     * @param text �����˵��ı�
     * @return ���˺���ı�
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        // ָ��1
        TrieNode tempNode = rootNode;
        // ָ��2
        int begin = 0;
        // ָ��3
        int position = 0;
        // ���
        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // ��������
            if (isSymbol(c)) {
                // ��ָ��1���ڸ��ڵ�,���˷��ż�����,��ָ��2������һ��
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                // ���۷����ڿ�ͷ���м�,ָ��3��������һ��
                position++;
                continue;
            }

            // ����¼��ڵ�
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                // ��begin��ͷ���ַ����������д�
                sb.append(text.charAt(begin));
                // ������һ��λ��
                position = ++begin;
                // ����ָ����ڵ�
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // �������д�,��begin~position�ַ����滻��
                sb.append(REPLACEMENT);
                // ������һ��λ��
                begin = ++position;
                // ����ָ����ڵ�
                tempNode = rootNode;
            } else {
                // �����һ���ַ�
                position++;
            }
        }

        // �����һ���ַ�������
        sb.append(text.substring(begin));

        return sb.toString();
    }

    // �ж��Ƿ�Ϊ����
    private boolean isSymbol(Character c) {
        // 0x2E80~0x9FFF �Ƕ������ַ�Χ
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    // ǰ׺��
    private class TrieNode {

        // �ؼ��ʽ�����ʶ
        private boolean isKeywordEnd = false;

        // �ӽڵ�(key���¼��ַ�,value���¼��ڵ�)
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // ����ӽڵ�
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        // ��ȡ�ӽڵ�
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }

    }

}
