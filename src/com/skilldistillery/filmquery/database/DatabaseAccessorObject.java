package com.skilldistillery.filmquery.database;

import java.sql.*;
import java.util.*;

import com.skilldistillery.filmquery.entities.*;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private final static String USER = "student";
	private final static String PWD = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		Connection conn = DriverManager.getConnection(URL, USER, PWD);

		String sql = "SELECT * FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			// Here is our mapping of query columns to our object fields:
			String title = rs.getString("title");
			String desc = rs.getString("description");
			short releaseYear = rs.getShort("release_year");
			int langId = rs.getInt("language_id");
			int rentDur = rs.getInt("rental_duration");
			double rate = rs.getDouble("rental_rate");
			int length = rs.getInt("length");
			double repCost = rs.getDouble("replacement_cost");
			String rating = rs.getString("rating");
			String features = rs.getString("special_features");
			
			String lang = language(langId);
			List<Actor> actors = findActorsByFilmId(filmId);

			film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating, features, actors, lang);
		}
		
		conn.close();
		return film;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		Connection conn = DriverManager.getConnection(URL, USER, PWD);

		String sql = "SELECT * FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();

		System.out.println(stmt);
		
		if (actorResult.next()) {
			// Here is our mapping of query columns to our object fields:
			int id = actorResult.getInt("id");
			String fn = actorResult.getString("first_name");
			String ln = actorResult.getString("last_name");
			actor = new Actor(fn, ln, id);
		}

		conn.close();
		return actor;
	}

	@Override
	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PWD);
			String sql = "SELECT * FROM film JOIN film_actor ON film.id = film_actor.film_id WHERE actor_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				int filmId = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				short releaseYear = rs.getShort("release_year");
				int langId = rs.getInt("language_id");
				int rentDur = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double repCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				String features = rs.getString("special_features");

				String lang = language(langId);
				List<Actor> actors = findActorsByFilmId(filmId);
				
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating, features, actors, lang);
				films.add(film);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PWD);
			String sql = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id WHERE film_actor.film_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				// Here is our mapping of query columns to our object fields:
				int id = rs.getInt("id");
				String fn = rs.getString("first_name");
				String ln = rs.getString("last_name");
				Actor actor = new Actor(fn, ln, id);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actors;
	}

	@Override
	public List<Film> findFilmByKeywords(String keyword) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PWD);
			String sql = "SELECT DISTINCT film.* FROM film JOIN film_actor ON film.id = film_actor.film_id WHERE film.title LIKE ? OR film.description LIKE ?";
			
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+keyword+"%");
			stmt.setString(2, "%"+keyword+"%");
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				int filmId = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				short releaseYear = rs.getShort("release_year");
				int langId= rs.getInt("language_id");
				int rentDur = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double repCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				String features = rs.getString("special_features");

				String lang = language(langId);
				List<Actor> actors = findActorsByFilmId(filmId);
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating, features, actors, lang);
				films.add(film);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}
	
	public String language(int langId) {
		String lang = null;
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PWD);
			String sql = "SELECT name FROM language WHERE id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, langId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				lang = rs.getString("name");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lang;
		
	}

}
