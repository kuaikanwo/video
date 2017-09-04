package com.core.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.core.mapper.TokenMapper;
import com.core.service.SystemServiceI;

@Component
public class SystemTask {
	
	@Autowired
	private SystemServiceI systemService;
	
	@Scheduled(cron = "0 13 11 * * ?") // 间隔5秒执行
    public void taskCycle() {
		systemService.addPlayGross();
    }
}
