package com.mentalabs.christmasTree;

public class Test {
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void main(String[] args) throws Exception {
		Albero a = new Albero(21, 40);
		for (int i = 0; i < 11; i++) {

			clearScreen();
			System.out.println(a.toString());
			Thread.sleep(500);
			clearScreen();
			a.nextFrame();
			System.out.println(a.toString());
			Thread.sleep(500);
		}
	}
}
