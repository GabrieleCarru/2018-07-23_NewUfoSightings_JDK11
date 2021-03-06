package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.newufosightings.model.Adiacenza;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings(int anno, String shape) {
		String sql = "select * " + 
				"from sighting " + 
				"where year(`datetime`) = ? " + 
				"and `shape` = ? ";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setString(2, shape);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				list.add(new Sighting(rs.getInt("id"), rs.getTimestamp("datetime").toLocalDateTime(),
						rs.getString("city"), rs.getString("state"), rs.getString("country"), rs.getString("shape"),
						rs.getInt("duration"), rs.getString("duration_hm"), rs.getString("comments"),
						rs.getDate("date_posted").toLocalDate(), rs.getDouble("latitude"),
						rs.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
	}

	public List<State> loadAllStates(Map<String, State> idMap) {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(state);
				idMap.put(rs.getString("id"), state);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<String> getAllShapeByAnno(int anno) {
		
		String sql = "select distinct `shape` as forma " + 
				"from sighting " + 
				"where year(`datetime`) = ? " + 
				"order by shape ";
		List<String> result = new ArrayList<String>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getString("forma"));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	public List<Adiacenza> getAdiacenze(int anno, String shape, Map<String, State> idMap) {
		
		String sql = "select distinct(n.`state1`) as s1, n.`state2` as s2, " + 
				"count(distinct(s.`id`)) as peso " + 
				"from sighting s, neighbor n " + 
				"where s.`shape` = ? and year(s.`datetime`) = ? " + 
				"and (s.`state` = n.`state1` || s.`state` = n.`state2`) " + 
				"group by n.`state1`, n.`state2` ";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, shape);
			st.setInt(2, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State s1 = idMap.get(rs.getString("s1"));
				State s2 = idMap.get(rs.getString("s2"));
				result.add(new Adiacenza(s1, s2, rs.getInt("peso")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

}

