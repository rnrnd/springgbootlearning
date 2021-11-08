package com.example.demo.service.impl;

import org.example.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public String getOne() {
		return this.getClass().getName();
	}
}
