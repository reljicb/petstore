package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.api.model.User;
import com.example.api.repositories.UserRepository;

import io.jsonwebtoken.Claims;

public class AuthenticatorInterceptor implements HandlerInterceptor
{
	@Autowired
	private UserRepository userRepo;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	      throws Exception
	{
		final Claims claims = (Claims) request.getAttribute("claims");
		if (claims == null)
			return true; // assumes logging page

		String username = claims.getSubject();

		User user = userRepo.getByUsername(username);
		if (user == null)
		{ // claim expiration check can be added here, too
			throw new ServletException("Invalid login");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	      ModelAndView modelAndView) throws Exception
	{
		// log.info("---method executed---");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	      throws Exception
	{
		// log.info("---Request Completed---");
	}
}
