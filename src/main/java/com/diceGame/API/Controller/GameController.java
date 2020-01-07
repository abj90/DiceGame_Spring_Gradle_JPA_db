package com.diceGame.API.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diceGame.API.Domain.Game;
import com.diceGame.API.Service.GameService;


@RestController
@RequestMapping(path="/players")
public class GameController {
	
	
	@Autowired
	private GameService gameService;
	 
	//Post play a game request
	 @PostMapping("/{userId}/games")
	 public ResponseEntity<String> addGame(@PathVariable int userId) {
		 
		 boolean isPlayerFound = gameService.addGameDB(userId);
		 
		 if(isPlayerFound) {
			 return new ResponseEntity<>("ok",HttpStatus.OK);
		 }else {
			 return new ResponseEntity<>("User was not found",HttpStatus.NOT_FOUND);
		 }
	 }
	 //get all games 
	 @GetMapping(path="/games")
	 public ArrayList<Game> getAllGames(){
		 return gameService.getGames();
	 }
	 
	 //get games by userId
	 @GetMapping(path="/{userId}/games")
	 public ArrayList<Game> getAllGamesById(@PathVariable int userId){
		 return gameService.getGamesByUserid(userId);
	 }
	
	
}
