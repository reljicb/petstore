package com.example.api.controllers;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean
{
	private static final String BEARER_ = "Bearer ";

	@Override
	public void doFilter(final ServletRequest req,
	      final ServletResponse res,
	      final FilterChain chain) throws IOException, ServletException
	{
		final HttpServletRequest request = (HttpServletRequest) req;

		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith(BEARER_))
		{
			throw new ServletException("Missing or invalid Authorization header.");
		}

		final String token = authHeader.substring(BEARER_.length()); // The part after "Bearer "

		try
		{
			final Claims claims = UserController.setParsingKey(Jwts.parser())
			      .parseClaimsJws(token).getBody();
			request.setAttribute("claims", claims);
		}
		catch (final SignatureException e)
		{
			throw new ServletException("Invalid token.");
		}

		chain.doFilter(req, res);
	}
}
