package jwernikowski.application;

import jwernikowski.domain.Game;
import jwernikowski.domain.GameRepository;
import jwernikowski.domain.Score;
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

		Game game = gameRepository.get(gameId);

		gameRepository.delete(game);
	}

	public void updateScore(UpdateScoreCommand command) {

		Game game = gameRepository.get(command.gameId());

		Score homeTeamScore = new Score(command.homeTeamScore());
		Score awayTeamScore = new Score(command.awayTeamScore());

		game.updateScore(homeTeamScore, awayTeamScore);
	}

	public BoardSummaryDto getSummary() {

		throw new IllegalStateException("not implemented");
	}
}
