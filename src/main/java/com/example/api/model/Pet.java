package com.example.api.model;

import static com.example.utils.StringUtils.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
// @Table(name = "pets")
public class Pet
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String rase;

	private String owner;

	private String color;

	private String skill;

	protected Pet()
	{
	}

	public Pet(String name, String rase, String owner)
	{
		this.name = checkNotNullNorEmpty(name);
		this.rase = checkNotNullNorEmpty(rase);
		this.owner = checkNotNullNorEmpty(owner);
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getRase()
	{
		return rase;
	}

	public String getOwner()
	{
		return owner;
	}

	public String getColor()
	{
		return color;
	}

	public Pet setColor(String color)
	{
		this.color = color;
		return this;
	}

	public String getSkill()
	{
		return skill;
	}

	public Pet setSkill(String skill)
	{
		this.skill = skill;
		return this;
	}

	@Override
	public String toString()
	{
		return String.format(
		      "Customer[id=%d, name='%s', rase='%s']",
		      id, name, rase);
	}
}
