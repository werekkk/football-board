package jwernikowski.application;

import jwernikowski.domain.GameRepository;

public class GameApplicationService {

	private final GameRepository gameRepository;

	public GameApplicationService(GameRepository gameRepository) {

		this.gameRepository = gameRepository;
	}

	/**
	 * @return id of created football game
	 */
	public long startGame(StartGameCommand command) {

		throw new IllegalStateException("not implemented");
	}
}
