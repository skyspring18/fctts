package com.a09.tts.service;

import com.a09.tts.util.JsonToEntity;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 语音转文本（ASR）服务类
 */
@Service
public class ASRService {

    public String transcribe(String filePath, String language) {
        // 创建 JSON 解析工具
        JsonToEntity jsonToEntity = new JsonToEntity();

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 定义 ASR API 请求地址（**请替换为你的 API 地址**）
        String url = "http://vop.baidu.com/server_api";

        // **你的 access_token（请替换为实际值）**
        String accessToken = "24.139c7db7ffff541178434f9e535282f6.2592000.1742436048.282335-117574";

        // --- 1. 构造请求参数 ---
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("file", new FileSystemResource(new File(filePath))); // 音频文件
        requestBody.add("language", language); // 选择语言，例如 "zh"（中文）或 "en"（英文）

        // --- 2. 构造 Headers 设置 access_token ---
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
        headers.set("Authorization", "Bearer " + accessToken); // **关键部分：添加 access_token 认证**

        // --- 3. 创建 HttpEntity 对象 ---
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // --- 4. 发送 POST 请求 ---
            ResponseEntity<String> response = restTemplate.postForEntity(new URI(url), requestEntity, String.class);

            // 打印 API 返回的 JSON 响应
            System.out.println(response.getBody());

            // --- 5. 解析 JSON 响应，提取识别的文本内容 ---
            String resultText = jsonToEntity.oneAttribute("text", response.getBody());
            return resultText;
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI: " + e.getMessage());
            return null;
        }
    }
}