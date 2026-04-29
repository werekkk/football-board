package jwernikowski.application;

public record UpdateScoreCommand(
	long gameId,
	int homeTeamScore,
	int awayTeamScore
) {
}
