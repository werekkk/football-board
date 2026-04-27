package jwernikowski.application;

public record StartGameCommand(
	String homeTeam,
	String awayTeam
) {
}
