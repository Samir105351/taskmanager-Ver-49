package com.samir.taskmanager;

import com.samir.taskmanager.dto.TaskList;
import com.samir.taskmanager.repository.LowLevelTaskRepository;
import com.samir.taskmanager.service.TaskListService;
import com.samir.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskmanagerApplicationTests {

	@Test
	void contextLoads() {


	}

	@Test
	public void test() {
		System.out.println("Hello world!");
	}

}
