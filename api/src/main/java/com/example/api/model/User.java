package com.example.api.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.utils.StringUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Entity
public class User
{
	private static final String SEP = ",";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;
	private String password;
	private String roles;

	protected User()
	{
	}

	public User(String username, String password)
	{
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public List<String> getRoles()
	{
		if (Strings.isNullOrEmpty(roles))
			return Lists.newArrayList();

		return Lists.newArrayList(Splitter.on(SEP).splitToList(roles));
	}

	public User addRoles(String role)
	{
		List<String> roles = getRoles();
		roles.add(StringUtils.checkNotNullNorEmpty(role));
		this.roles = Joiner.on(SEP).join(roles);

		return this;
	}
}
