package jwernikowski.application;

import jwernikowski.domain.Game;
import jwernikowski.domain.GameRepository;
import jwernikowski.domain.Team;

public class GameApplicationService {

	private final GameRepository gameRepository;

	public GameApplicationService(GameRepository gameRepository) {

		this.gameRepository = gameRepository;
	}

	/**
	 * @return id of created football game
	 */
	public long startGame(StartGameCommand command) {

		Team homeTeam = new Team(command.homeTeam());
		Team awayTeam = new Team(command.awayTeam());

		Game game = new Game(
			homeTeam,
			awayTeam
		);

		gameRepository.save(game);

		return game.getId();
	}

	public void finishGame(long gameId) {

	}
}
