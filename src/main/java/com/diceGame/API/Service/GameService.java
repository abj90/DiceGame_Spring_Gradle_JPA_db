package com.diceGame.API.Service;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diceGame.API.Domain.Game;
import com.diceGame.API.Repository.GameRepository;
import com.diceGame.API.Repository.UserRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	// adding a game in db
	public boolean addGameDB(int userId) {
		
		if(userRepository.existsById(userId)) {
			Game game= new Game(userId);
			game.setFirstDice();
			game.setSecondDice();
			game.setIsGameWon();
			gameRepository.save(game);
			return true;
		}else {
			return false;
		}
	}
	
	// get all games
	public ArrayList<Game> getGames(){
		
		ArrayList<Game> games = new ArrayList<Game>();
		
		gameRepository.findAll().forEach(game -> games.add(game));
		
		return games;
		
	}
	
	// get games By userId
	public ArrayList<Game> getGamesByUserid(int userId){
		
		return gameRepository.findByUserId(userId);
	}
	
	
	//delete games by userId
	public void deleteGameByUserId(int userId) {
		
		ArrayList<Game> gamesByUserId = this.getGamesByUserid(userId);
		
		for (Game game : gamesByUserId) {
			gameRepository.deleteById(game.getId());
			
		}
		
		
	}
	

}
