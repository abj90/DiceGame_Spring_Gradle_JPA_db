package com.diceGame.API.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Games")
public class Game {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private int firstDice;
	private int secondDice;
	private boolean isGameWon;
	
	@ManyToOne
	private User user;
	
	public Game() {}
	
	public Game(int id) {
		super();
		this.user = new User(id);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFirstDice() {
		return firstDice;
	}

	public void setFirstDice() {
		this.firstDice = (int)(Math.random()*((6-1)+1))+1;
			
	}

	public int getSecondDice() {
		return secondDice;
	}

	public void setSecondDice() {
		this.secondDice = (int)(Math.random()*((6-1)+1))+1;;
	}

	public boolean getIsGameWon() {
		return isGameWon;
	}

	public void setIsGameWon() {
		
		if( (this.firstDice + this.secondDice) == 7) {
			this.isGameWon = true;
		}else {
			this.isGameWon = false;
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
