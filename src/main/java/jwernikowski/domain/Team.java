package jwernikowski.domain;

public record Team(String name) {

	public Team {

		if (invalidName(name)) {

			throw new IllegalArgumentException("Team name invalid");
		}
	}

	private static boolean invalidName(String name) {

		return name == null || name.isBlank();
	}
}
