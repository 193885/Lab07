package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.NercIdMap;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {

	public List<Nerc> getNercList(NercIdMap nercIdMap) {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				
				nercList.add(nercIdMap.get(n));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	
	public List <PowerOutage> getAllPow(){
		 
		String sql = "SELECT id, customers_affected, date_event_began, date_event_finished, nerc_id FROM poweroutages";
		
		List <PowerOutage> pows = new ArrayList<>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				PowerOutage p = new PowerOutage (res.getInt("id"), res.getInt("customers_affected"), res.getTimestamp("date_event_began").toLocalDateTime(), 
												res.getTimestamp("date_event_finished").toLocalDateTime(), res.getInt("nerc_id") );
				
				pows.add(p);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return pows;
	}
}
