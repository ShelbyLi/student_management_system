package com.shelby;

import com.shelby.entity.Admin;
import com.shelby.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentManagementSystemApplicationTests {

	@Autowired
	AdminServiceImpl adminService;

	@Test
	void contextLoads() {
		Admin admin = adminService.queryByName("root");
		System.out.println(admin.getId());
	}

}
