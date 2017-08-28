package com.core.service;


import com.core.model.Token;
/**
 * @author cuimengtao
 *
 */
public interface TokenServiceI {
	public void add(Token token);
	public void update(Token token);
	public Token getTokenByUserId(String userId);
}
