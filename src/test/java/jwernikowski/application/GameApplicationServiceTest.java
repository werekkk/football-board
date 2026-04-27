package jwernikowski.application;

import jwernikowski.domain.Game;
import jwernikowski.domain.GameRepository;
import jwernikowski.infrastructure.InMemoryGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
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
		assertThat(game.id()).isEqualTo(gameId);
		assertThat(game.awayTeam().name()).isEqualTo(command.awayTeam());
		assertThat(game.homeTeam().name()).isEqualTo(command.homeTeam());
		assertThat(game.awayTeamScore().value()).isEqualTo(0);
		assertThat(game.homeTeamScore().value()).isEqualTo(0);
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
}