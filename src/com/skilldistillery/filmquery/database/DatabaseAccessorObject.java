package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";

	private String user = "student";
	private String pass = "student";

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;

		try {Connection conn = DriverManager.getConnection(URL, user, pass);
//				String sql = "SELECT * FROM film WHERE id = ?";
		String sql = "SELECT id, title, description, release_year, language_id,"
                + " rental_duration, rental_rate, length, replacement_cost,"
		         + " rating, special_features "
             +"FROM film WHERE id = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, filmId);
				ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				film = new Film();
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setRentalDuration(rs.getInt("rental_duration"));
				film.setRentalRate(rs.getInt("rental_rate"));
				film.setLength(rs.getInt("length"));
				film.setReplacementCost(rs.getDouble("replacement_cost"));
				film.setRatina(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("special_features"));		
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}


	@Override
	public Actor findActorById(int actorId) {
		Actor actor;
		
		try {Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT a.id, a.first_name, a.last_name, film.id" + " FROM film_actor f"
                + " JOIN actor a ON f.actor_id = a.id" + " JOIN film ON f.film_id = film.id" + " WHERE film.id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, actorId);
			ResultSet rs = ps.executeQuery();
			
			
			
			
			
			return actor;
	}

	
	
	
	
	
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		
		return actors;
	}
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Driver not found.");
			throw new RuntimeException("**Unable to load MySQL Driver class");
		}
	}
}