package com.a09.tts.controller;

import com.a09.tts.service.ASRService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 语音转文本的控制器类
 *
 * @author LZH
 */

@Slf4j
@RestController
@RequestMapping("/voice")
public class ASRController {

    //语音转文本临时音频文件的存储路径
    @Value("${app.asr-dir}")
    private String asrDir;

    @Autowired
    private ASRService asrService;

    /**
     * 语音转文本接口
     *
     * @param file 上传的语音文件
     * @param language 语言代码（可选，默认为 "zh"）
     * @return 返回识别出的文本
     */
    @PostMapping("/transcribe")
    public ResponseEntity<String> transcribe(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "language", defaultValue = "zh") String language) {

        try {
            //检测音频文件是否为空
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("音频文件为空！");
            }

            // 使用配置的路径
            Path dirPath = Paths.get(asrDir);
            //指定音频文件名
            String audioFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            // 构建完整路径
            Path audioFilePath = dirPath.resolve(audioFileName);
            // 保存文件
            Files.copy(file.getInputStream(), audioFilePath, StandardCopyOption.REPLACE_EXISTING);
            // 调用 ASRService 进行语音转文本
            String resultText = asrService.transcribe(audioFilePath.toString(), language);

            // 删除临时文件
            audioFilePath.toFile().deleteOnExit();

            return ResponseEntity.ok(resultText);
        } catch (IOException e) {
            log.error("文件处理失败", e);
            return ResponseEntity.status(500).body("语音文件处理失败");
        }
    }
}