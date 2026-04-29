package jwernikowski.application;

import jwernikowski.domain.Game;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;

class BoardSummaryDtoFactory {

	BoardSummaryDto create(List<Game> games) {

		List<BoardSummaryDto.BoardGameDto> gameDtos = games.stream()
			.sorted(byTotalScoreAndId())
			.map(this::createGame)
			.toList();

		return new BoardSummaryDto(gameDtos);
	}

	private Comparator<Game> byTotalScoreAndId() {

		return byTotalScore().thenComparing(byId());
	}

	private Comparator<Game> byTotalScore() {

		return comparingInt(Game::getTotalScore).reversed();
	}

	private Comparator<Game> byId() {

		return comparingLong(Game::getId).reversed();
	}

	private BoardSummaryDto.BoardGameDto createGame(Game game) {

		return new BoardSummaryDto.BoardGameDto(
			game.getHomeTeam().name(),
			game.getAwayTeam().name(),
			game.getHomeTeamScore().value(),
			game.getAwayTeamScore().value()
		);
	}
}
