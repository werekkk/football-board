package jwernikowski.domain;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

	Game get(long id);

	List<Game> getAll();

	Optional<Game> find(long id);

	void save(Game game);

	void delete(Game game);
}
