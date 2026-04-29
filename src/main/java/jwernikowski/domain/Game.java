package jwernikowski.domain;

public class Game {

	private long id;
	private final Team homeTeam;
	private final Team awayTeam;
	private Score homeTeamScore;
	private Score awayTeamScore;

	public Game(
		Team homeTeam,
		Team awayTeam
	) {

		if (teamsInvalid(homeTeam, awayTeam)) {

			throw new IllegalArgumentException(String.format("Creating a game with invalid teams: $s and $s", homeTeam, awayTeam));
		}

		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeTeamScore = new Score(0);
		this.awayTeamScore = new Score(0);
	}

	private static boolean teamsInvalid(Team homeTeam, Team awayTeam) {

		return homeTeam.equals(awayTeam);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public Score getHomeTeamScore() {
		return homeTeamScore;
	}

	public Score getAwayTeamScore() {
		return awayTeamScore;
	}

	public void updateScore(Score homeTeamScore, Score awayTeamScore) {

		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
	}
}
