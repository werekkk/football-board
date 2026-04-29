package jwernikowski.application;

import jwernikowski.domain.Game;
import jwernikowski.domain.GameRepository;
import jwernikowski.domain.Team;
import jwernikowski.infrastructure.InMemoryGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameApplicationServiceTest {

	private GameRepository gameRepository;
	private GameApplicationService applicationService;

	@BeforeEach
	void setUp() {

		gameRepository = new InMemoryGameRepository();
		applicationService = new GameApplicationService(gameRepository);
	}

	@Test
	void shouldStartGame() {

		// given
		StartGameCommand command = new StartGameCommand("Mexico", "Canada");

		// when
		long gameId = applicationService.startGame(command);
		Game game = gameRepository.get(gameId);

		// then
		assertThat(gameId).isGreaterThan(0L);
		assertThat(game.getId()).isEqualTo(gameId);
		assertThat(game.getAwayTeam().name()).isEqualTo(command.awayTeam());
		assertThat(game.getHomeTeam().name()).isEqualTo(command.homeTeam());
		assertThat(game.getAwayTeamScore().value()).isEqualTo(0);
		assertThat(game.getHomeTeamScore().value()).isEqualTo(0);
	}

	@ParameterizedTest
	@MethodSource
	void shouldNotStartInvalidGame(StartGameCommand command) {

		// when & then
		assertThrows(
			IllegalArgumentException.class,
			() -> applicationService.startGame(command)
		);
	}

	private static Stream<StartGameCommand> shouldNotStartInvalidGame() {

		return Stream.of(
			new StartGameCommand(null, null),
			new StartGameCommand("", ""),
			new StartGameCommand("Mexico", "Mexico")
		);
	}

	@Test
	void shouldFinishExistingGame() {

		// given
		Game game = new Game(new Team("Spain"), new Team("Brazil"));
		gameRepository.save(game);

		// when & then
		applicationService.finishGame(game.getId());
		assertThrows(
			IllegalArgumentException.class,
			() -> gameRepository.get(game.getId())
		);
	}

	@Test
	void shouldNotFinishNonExistingGame() {

		// given
		Game game = new Game(new Team("Spain"), new Team("Brazil"));
		gameRepository.save(game);

		// when & then
		assertThrows(
			IllegalArgumentException.class,
			() -> applicationService.finishGame(game.getId() + 100L)
		);
	}

	@Test
	void shouldUpdateScore() {

		// given
		Game game1 = new Game(new Team("Spain"), new Team("Brazil"));
		Game game2 = new Game(new Team("Germany"), new Team("France"));

		gameRepository.save(game1);
		gameRepository.save(game2);

		// when
		applicationService.updateScore(new UpdateScoreCommand(
			game1.getId(),
			1,
			5
		));

		// then
		assertThat(game1.getHomeTeamScore().value()).isEqualTo(1);
		assertThat(game1.getAwayTeamScore().value()).isEqualTo(5);
		assertThat(game2.getHomeTeamScore().value()).isEqualTo(0);
		assertThat(game2.getAwayTeamScore().value()).isEqualTo(0);
	}

	@ParameterizedTest
	@MethodSource
	void shouldNotUpdateScore(boolean correctId, int homeScore, int awayScore) {

		// given
		Game game = new Game(new Team("Spain"), new Team("Brazil"));
		UpdateScoreCommand command = new UpdateScoreCommand(
			correctId ? game.getId() : game.getId() + 100L,
			homeScore,
			awayScore
		);

		// when & then
		assertThrows(
			IllegalArgumentException.class,
			() -> applicationService.updateScore(command)
		);
	}

	private static Stream<Arguments> shouldNotUpdateScore() {

		return Stream.of(
			Arguments.of(false, 5, 6),
			Arguments.of(true, -12, 6),
			Arguments.of(true, 5, -4323443)
		);
	}

	@Test
	void shouldReturnEmptySummary() {

		// when
		BoardSummaryDto summary = applicationService.getSummary();

		// then
		assertThat(summary.games()).isEmpty();
	}

	@Test
	void shouldReturnSummary() {

		// given
		gameWithScore("Mexico", "Canada", 0, 5);
		gameWithScore("Spain", "Brazil", 10, 2);
		gameWithScore("Germany", "France", 2, 2);
		gameWithScore("Uruguay", "Italy", 6, 6);
		gameWithScore("Argentina", "Australia", 3, 1);

		// when
		BoardSummaryDto summary = applicationService.getSummary();

		// then
		assertThat(summary.games()).containsExactly(
			new BoardSummaryDto.BoardGameDto("Uruguay", "Italy", 6, 6),
			new BoardSummaryDto.BoardGameDto("Spain", "Brazil", 10, 2),
			new BoardSummaryDto.BoardGameDto("Mexico", "Canada", 0, 5),
			new BoardSummaryDto.BoardGameDto("Argentina", "Australia", 3, 1),
			new BoardSummaryDto.BoardGameDto("Germany", "France", 2, 2)
		);
	}

	private void gameWithScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {

		long gameId = applicationService.startGame(new StartGameCommand(
			homeTeam,
			awayTeam
		));

		applicationService.updateScore(new UpdateScoreCommand(
			gameId,
			homeTeamScore,
			awayTeamScore
		));
	}
}