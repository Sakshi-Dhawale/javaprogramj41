package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.PlayerRepository;
import com.exception.PlayerNotFoundException;
import com.model.Player;

@Service
public class PlayerServiceImpl implements PlayerService{
	@Autowired
	PlayerRepository playerRepo;

	@Override
	public Player savePlayer(Player p) {	
		return playerRepo.save(p);
	}

	@Override
	public Player getOnePlayer(int playerId) {
		
		return playerRepo.findById(playerId).orElse(null);
	}
	
	@Override
	public List<Player> getAllPlayer() {
		
		return playerRepo.findAll();
	}
    
	@Override
	public Map<String, Object> deletePlayer(int playerId) throws PlayerNotFoundException
	{
		Map<String,Object> response=new HashMap<String,Object>();
		try {
			Player player=playerRepo.findById(playerId).orElseThrow(()->new PlayerNotFoundException("Player not Found"));
			
			playerRepo.delete(player);
			response.put("deleted",Boolean.TRUE);
			
		}catch(PlayerNotFoundException p)
		{
			response.put("not deleted",p.getMessage());
		}
		return response;
	}


	@Override
	public Player updatePlayer(Player player) {
		Player  p=null;
		try {
	    p=playerRepo.findById(player.getPlayerId()).orElseThrow(()->new PlayerNotFoundException("Player  not exist"));
		
		p.setPlayerName(player.getPlayerName());
		}catch(PlayerNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		return playerRepo.save(p);
		
	}
}
