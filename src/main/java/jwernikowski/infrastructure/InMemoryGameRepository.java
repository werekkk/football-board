package jwernikowski.infrastructure;

import jwernikowski.domain.Game;
import jwernikowski.domain.GameRepository;

public class InMemoryGameRepository implements GameRepository {

	@Override
	public Game get(long id) {

		throw new IllegalStateException("not implemented");
	}
}
