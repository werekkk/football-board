package jwernikowski.domain;

public record Score(int value) {

	public Score {

		if (invalidScore(value)) {

			throw new IllegalArgumentException("Creating Score with invalid value: " + value);
		}
	}

	private static boolean invalidScore(int value) {

		return value < 0;
	}
}
