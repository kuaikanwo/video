package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.mapper.TokenMapper;
import com.core.model.Token;
import com.core.service.TokenServiceI;
import com.core.util.DateUtils;
import com.core.util.UUIDGenerator;

@Component
public class TokenServiceImp implements TokenServiceI{
    @Autowired
	private TokenMapper tokenMapper;

	@Override
	public void add(Token token) {
		token.setId(UUIDGenerator.generate());
		token.setLastUpdateTime(DateUtils.getDate());
		tokenMapper.add(token);
	}

	@Override
	public Token getTokenByUserId(String userId) {
		return tokenMapper.getTokenByUserId(userId);
	}

	@Override
	public void update(Token token) {
		token.setLastUpdateTime(DateUtils.getDate());
		tokenMapper.update(token);
	}
}
