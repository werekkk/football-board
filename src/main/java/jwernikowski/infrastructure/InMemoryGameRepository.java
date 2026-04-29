package jwernikowski.infrastructure;

import jwernikowski.domain.Game;
import jwernikowski.domain.GameRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryGameRepository implements GameRepository {

	private Map<Long, Game> games = new HashMap<>();
	private long nextId = 1;

	@Override
	public Game get(long id) {

		return find(id)
			.orElseThrow(() -> new IllegalArgumentException("Game with id " + id + " not found"));
	}

	@Override
	public List<Game> getAll() {

		return games.values()
			.stream()
			.toList();
	}

	@Override
	public Optional<Game> find(long id) {

		return Optional.ofNullable(games.get(id));
	}

	@Override
	public void save(Game game) {

		game.setId(nextId++);

		games.put(game.getId(), game);
	}

	@Override
	public void delete(Game game) {

		games.remove(game.getId());
	}
}
