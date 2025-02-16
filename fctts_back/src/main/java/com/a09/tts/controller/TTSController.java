package com.a09.tts.controller;

import com.a09.tts.service.TTSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/voice")
public class TTSController {

    @Autowired
    private TTSService ttsService;

    /**
     * 处理tts的控制器方法
     *
<<<<<<< HEAD
     * @param requestData 参数是包含文本和声音样本选择的json字符串
=======
     * @param requestData
>>>>>>> d7acfc40e98279259de229705137c2bbcf0dc2b0
     * @return 返回的是生成的语音url，前端进入网址即可收听语音
     */

    @PostMapping("/synthesize")
    public String synthesize(@RequestBody Map<String, Object> requestData) {
        String text = (String) requestData.get("text");
        String voice = (String) requestData.get("voice");
        return ttsService.speak(text,voice);
    }

<<<<<<< HEAD
=======
    /**
     * 使tts能够选择预设音色的控制器方法
     *
     * @param requestData
     * @return
     */

//    @PostMapping("/select")
//    public String select(@RequestBody Map<String, Object> requestData) {
//
//    }
>>>>>>> d7acfc40e98279259de229705137c2bbcf0dc2b0
}
