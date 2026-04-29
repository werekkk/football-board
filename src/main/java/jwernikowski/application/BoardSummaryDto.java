package jwernikowski.application;

import java.util.List;

public record BoardSummaryDto(
	List<BoardGameDto> games
) {
	public record BoardGameDto(
		String homeTeam,
		String awayTeam,
		int homeTeamScore,
		int awayTeamScore
	) {

		int totalScore() {

			return homeTeamScore + awayTeamScore;
		}
	}
}
