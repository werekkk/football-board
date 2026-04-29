package jwernikowski.application;

import jwernikowski.domain.Game;
import jwernikowski.domain.GameRepository;
import jwernikowski.domain.Team;
import jwernikowski.infrastructure.InMemoryGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
}