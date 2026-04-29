package jwernikowski.domain;

import java.util.Optional;

public interface GameRepository {

	Game get(long id);

	Optional<Game> find(long id);

	void save(Game game);

	void delete(Game game);
}
