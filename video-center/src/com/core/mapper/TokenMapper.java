package com.core.mapper;

import com.core.model.Token;
/**
 * 
 * @author cuimengtao
 *
 */
public interface TokenMapper {
	public void add(Token token);
	public void update(Token token);
	public Token getTokenByUserId(String userId);
}
