package jwernikowski.infrastructure;

import jwernikowski.domain.Game;
import jwernikowski.domain.GameRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryGameRepository implements GameRepository {

	private Map<Long, Game> games = new HashMap<>();
	private long nextId = 1;

	@Override
	public Game get(long id) {

		return games.get(id);
	}

	@Override
	public void save(Game game) {

		game.setId(nextId++);

		games.put(game.getId(), game);
	}
}
