package jwernikowski.domain;

public record Game(
	long id,
	Team homeTeam,
	Team awayTeam,
	Score homeTeamScore,
	Score awayTeamScore
) {}
