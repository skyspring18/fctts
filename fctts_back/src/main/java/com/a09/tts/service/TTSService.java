package com.a09.tts.service;

import com.a09.tts.util.JsonToEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 文本转语音服务类
 *
 */

@Service
public class TTSService {

    /**
     * 用于接收客户端传来的文本，并通过Restful API技术使用chatTTS API接口
     *
     * @param text
     */

<<<<<<< HEAD
    public String speak(String text,String voice) {
=======
    public String speak(String text) {
>>>>>>> d7acfc40e98279259de229705137c2bbcf0dc2b0
        //创建JsonToEntity示例，方便后续对json字符串的相关操作
        JsonToEntity jsonToEntity = new JsonToEntity();

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 定义请求地址
        String url = "http://127.0.0.1:9966/tts";

        // 定义请求参数
        Map<String, Object> requestBody = new HashMap<>();
<<<<<<< HEAD
        requestBody.put("text", text);//文本
        requestBody.put("voice", voice);//音色，可变，2222/7777/8888/9999...
=======
        requestBody.put("text", text);
        requestBody.put("voice", "9999");//音色，可变
>>>>>>> d7acfc40e98279259de229705137c2bbcf0dc2b0
        requestBody.put("prompt", "");
        requestBody.put("temperature", 0.3);
        requestBody.put("top_p", 0.7);
        requestBody.put("top_k", 20);
        requestBody.put("skip_refine", 0);
        requestBody.put("custom_voice", 0);

        // 将 Map 转换为 MultiValueMap
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
            formData.add(entry.getKey(), entry.getValue().toString());
        }

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 修改此处，将单个 Charset 对象包装成 List<Charset>
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));

        // 创建 HttpEntity 对象，包含请求体和请求头
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        try {
            // 发送 POST 请求并获取响应
            ResponseEntity<String> response = restTemplate.postForEntity(new URI(url), requestEntity, String.class);

            // 打印响应结果
            System.out.println(response.getBody());

            //从相应结果中取出生成的语音url，并返回给前端
            String s = jsonToEntity.oneAttribute("url", response.getBody());
            return s;
        } catch (URISyntaxException e) {
            // 更详细的异常处理，例如日志记录
            System.err.println("Invalid URI: " + e.getMessage());
            return null;
        }
    }


}
