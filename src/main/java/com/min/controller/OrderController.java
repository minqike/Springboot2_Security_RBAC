package com.min.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.entity.User;
import com.min.mapper.UserMapper;

@Controller
public class OrderController {
	// 首页
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	// 查询订单
	@RequestMapping("/order/show")
	public String showOrder() {
		return "showOrder";
	}

	// 添加订单
	@RequestMapping("/order/add")
	public String addOrder() {
		return "addOrder";
	}

	// 修改订单
	@RequestMapping("/order/update")
	public String updateOrder() {
		return "updateOrder";
	}

	// 删除订单
	@RequestMapping("/order/delete")
	public String deleteOrder() {
		return "deleteOrder";
	}

	// 自定义登陆页面
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@Autowired
	private UserMapper userMapper;

	@RequestMapping("/findUser")
	@ResponseBody
	public User findUser(String userName) {
		return userMapper.findByUsername(userName);
	}
}
