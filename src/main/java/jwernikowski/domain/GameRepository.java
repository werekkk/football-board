package jwernikowski.domain;

public interface GameRepository {

	Game get(long id);

	void save(Game game);
}
