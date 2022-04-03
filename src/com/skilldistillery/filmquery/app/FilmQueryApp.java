package com.skilldistillery.filmquery.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

//	private void test() {
//		Film film = db.findFilmById(1);
//		System.out.println(film);
////    Actor actor = db.findActorById(1);
////    System.out.println(actor);
////    System.out.println(film.getActorList());
//		for (Actor a : film.getActorList()) {
//			System.out.println(a);
//		}
//	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
		exitProgram();
	}

	private void startUserInterface(Scanner sc) {
		// Start menu loop, input, etc...

		System.out.println("Welcome to the video Look-Up");
		while (true) {
			System.out.println("---Please make a selection---");
			System.out.println("1) Look up a film by its Film ID");
			System.out.println("2) Look up a film by searching a keyword");
			System.out.println("3) Exit the application");

			try {
				int input = sc.nextInt();
				switch (input) {
				case 1:
					Film film = findFilmById(sc);
					if (film != null)
						;
					break;
				case 2:
					findFilmByKeyword(sc);
					break;
				case 3:
					return;
				default:
					System.out.println("| 1 | 2 | 3 | are the only available options");
				}

			} catch (InputMismatchException e) {
				System.out.println("*ERROR*  Please use | 1 | 2 | 3 |");
				sc.nextLine();

			}

		}

	}

	private Film findFilmById(Scanner sc) {
		System.out.println("What is the ID number of the movie you are looking up?");
		Film film = null;
		try {
			int input = sc.nextInt();
			film = db.findFilmById(input);
			if (film == null) {
				System.out.println("We do not have that film. Please try again");
			} else {
				film.setActorList(db.findActorsByFilmId(input));
				System.out.println(film.displayString());
				System.out.println("Language: " + db.findFilmLanguage(film.getLanguageId()));
				System.out.println("Cast: ");
				for (Actor actor : film.getActorList()) {
					System.out.println(actor.displayString());
				}

			}
		} catch (InputMismatchException e) {
			System.out.println("*ERROR* Please enter a valid number");
			sc.nextLine();
		}
		return film;
	}

	private void findFilmByKeyword(Scanner sc) {
		System.out.println("What is the keyword you are searching for");
		sc.nextLine();
		String keyword = sc.nextLine();
		List<Film> films = db.findFilmByKeyword(keyword);

		if (films.size() > 0) {
			for (Film film : films) {
				System.out.println(film.getDescription());
			}

		}

		if (films.size() < 1) {
			System.out.println("Unable to find any film using that keyword.  Please try again");
			return;
		} else {
			for (Film film : films) {
				film.setActorList(db.findActorsByFilmId(film.getId()));
				System.out.println(film.displayString());
				System.out.println("Language: " + db.findFilmLanguage(film.getLanguageId()));
				System.out.println("Cast: ");
				for (Actor actor : film.getActorList()) {
					System.out.println(actor.displayString());
				}
			}
		}
	}

	private void exitProgram() {
		System.out.println("Thank you for using the program!");
		System.exit(1);
	}

}