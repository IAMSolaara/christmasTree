//set package
package com.mentalabs.christmasTree;

import java.time.LocalTime;

public class Test {
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void main(String[] args) throws Exception {

		final int width = 21;
		final int height = 40;
		LocalTime goal = LocalTime.now().plusSeconds(10);
		Albero a = new Albero(width, height);
		clearScreen();
		System.out.println(a.toString());
		Thread.sleep(500);

		for (;;) {
			if (LocalTime.now().isAfter(goal)) a.accendi();
			clearScreen();
			a.nextFrame();
			System.out.println(a.toString());
			Thread.sleep(500);
		}
	}
}
