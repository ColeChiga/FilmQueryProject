package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.*;

import com.skilldistillery.filmquery.database.*;
import com.skilldistillery.filmquery.entities.*;

public class FilmQueryApp {
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//		try {
//			app.test();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		app.launch();
	}

	private void test() throws SQLException {
		Film film = db.findFilmById(1);
		System.out.println(film);

		Actor actor = db.findActorById(1);
		System.out.println(actor);
		List<Film> films = db.findFilmsByActorId(actor.getId());
		for (Film film1 : films) {
			System.out.println(film1);
		}

		Film film1 = db.findFilmById(1);
		printFilmTable(film1);
		System.out.println(film1);

		List<Actor> actors = db.findActorsByFilmId(1);
		for (Actor tempActor : actors) {
			System.out.println(tempActor);
		}

	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		try {
			startUserInterface(input);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {
		String userChoice;
		boolean loopIt = true;
		while (loopIt) {
			userMainMenu();
			userChoice = input.nextLine();

			if (userChoice.equalsIgnoreCase("one") || userChoice.equalsIgnoreCase("1")
					|| userChoice.equalsIgnoreCase("1.") || userChoice.equalsIgnoreCase("Look up film by id")
					|| userChoice.equalsIgnoreCase("id")) {
				userMenu1();
				int filmID;
				try {
					filmID = input.nextInt();
					input.nextLine();
				} catch (InputMismatchException ime) {
					input.nextLine();
					System.err.print("Invalid Input\n");
					continue;
				}
				Film film = db.findFilmById(filmID);
				if (film == null) {
					System.out.println("no film exists with that ID");
				} else {
					printFilmBasicTable(film);
				}
			} else if (userChoice.equalsIgnoreCase("two") || userChoice.equalsIgnoreCase("2")
					|| userChoice.equalsIgnoreCase("2.") || userChoice.equalsIgnoreCase("Look up film by keyword")
					|| userChoice.equalsIgnoreCase("keyword")) {

				userMenu2();
				String keyword = input.nextLine();
				List<Film> films = db.findFilmByKeywords(keyword);
				if (films.size() == 0) {
					System.out.println("no film exists with that keyword");
				} else {
				printFilmBasicTable(films);
				}
			} else if (userChoice.equalsIgnoreCase("three") || userChoice.equalsIgnoreCase("3")
					|| userChoice.equalsIgnoreCase("3.") || userChoice.equalsIgnoreCase("Exit the application")
					|| userChoice.equalsIgnoreCase("Exit")) {
				loopIt = false;
				userMenu3();
			} else {
				userErr();
			}

			System.out.println();
		}

	}

	private void printFilmTable(Film film) {
		System.out.printf("|%-4s | %-20s | %-100s | %-4s |%-7s | %-9s | %-6s | %-7s |%-9s | %-4s | %-60s ", "Id",
				"title", "description", "Year", "langId", "rent Dur", "rate", "length", "repCost", "rating",
				"features");
		System.out.println();
	}

	private void printFilmBasicTable(Film film) {
		System.out.println(
				" ____________________________________________________________________________________________________"
						+ "__________________________________________________________________________________________________> ");

		System.out.printf("| %-25s | %-4s  | %-5s | %-10s | %-110s | Actors: %n", "Title", "Year", "Rate", "Language",
				"Description");

		System.out.println(
				"|–––––––––––––––––––––––––––|–––––––|–––––––|––––––––––––|–––––––––––––––––––––––––––––––––––––––––––"
						+ "–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––|––––––––––––––––––––––––––––>");

		System.out.print(film.basicInfo());
		printActors(film.getActor());

		System.out.println();
		System.out.println(
				"|___________________________|_______|_______|____________|___________________________________________"
						+ "_____________________________________________________________________|____________________________>");
	}

	private void printFilmBasicTable(List<Film> films) {

		System.out.println(
				" ____________________________________________________________________________________________________"
						+ "__________________________________________________________________________________________________> ");
		System.out.printf("| %-25s | %-4s  | %-5s | %-10s | %-110s | Actors: %n", "Title", "Year", "Rate", "Language",
				"Description");
		System.out.println(
				"|–––––––––––––––––––––––––––|–––––––|–––––––|––––––––––––|–––––––––––––––––––––––––––––––––––––––––––"
						+ "–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––|––––––––––––––––––––––––––––>");

		for (Film film : films) {
			System.out.print(film.basicInfo());
			printActors(film.getActor());
			System.out.println();
		}

		System.out.println(
				"|___________________________|_______|_______|____________|___________________________________________"
						+ "_____________________________________________________________________|____________________________>");
	}

//
	public void printActors(List<Actor> actors) {

		for (Actor actor : actors) {
			System.out.printf(" %-21s ", (actor.getFullName() + ","));
		}
	}

//Menus

	private void userMainMenu() {
		System.out.println(" _____________________________");
		System.out.println("|          Film Menu          |");
		System.out.println("|–––––––––––––––––––––––––––––|");
		System.out.println("|                             |");
		System.out.println("| 1. Look up film by id       |");
		System.out.println("| 2. Look up film by keyword  |");
		System.out.println("| 3. Exit the application     |");
		System.out.println("|                             |");
		System.out.println("| Please pick an option above |");
		System.out.println("|_____________________________|");
	}

	private void userMenu1() {
		System.out.println(" _____________________________");
		System.out.println("|     Look up film by id      |");
		System.out.println("|–––––––––––––––––––––––––––––|");
		System.out.println("|                             |");
		System.out.println("|    Input your film's id     |");
		System.out.println("|_____________________________|");
	}

	private void userMenu2() {
		System.out.println(" _____________________________");
		System.out.println("|   Look up film by keyword   |");
		System.out.println("|–––––––––––––––––––––––––––––|");
		System.out.println("|                             |");
		System.out.println("|       Input a keyword       |");
		System.out.println("|        for your film        |");
		System.out.println("|_____________________________|");
	}

	private void userMenu3() {
		System.out.println(" _____________________________");
		System.out.println("|     Exiting the program     |");
		System.out.println("|–––––––––––––––––––––––––––––|");
		System.out.println("|                             |");
		System.out.println("|   Goodbye and Goodnight!    |");
		System.out.println("|_____________________________|");
	}

	private void userErr() {
		System.out.println(" _____________________________");
		System.out.println("|                             |");
		System.out.println("|        Invalid Input,       |");
		System.out.println("|       Please try again      |");
		System.out.println("|_____________________________|");
	}

//	question: why doesn't this code work?:
//	private void userErr() {
//		System.err.print(" _____________________________ \n");
//		System.err.print("|                             |\n");
//		System.err.print("|        Invalid Input,       |\n");
//		System.err.print("|       Please try again      |\n");
//		System.err.print("|_____________________________|\n");
//		System.out.println();
//	}
}
