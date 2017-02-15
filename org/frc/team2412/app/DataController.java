package org.frc.team2412.app;

import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataController implements Packet {

	private JSONObject obj;
	private JSONArray array;
	private final String name;
	
	public static final boolean ISOBJECT = true, ISARRAY = false;
	
	public DataController(boolean isObj, String name) {
		if (isObj == ISOBJECT) {
			obj = new JSONObject();
		} else {
			array = new JSONArray();
		}
		
		this.name = name;
	}

	@Override
	public String packetID() {
		return "DataController-"+name;
	}

	@Override
	public String[] packetData() {
		return obj == null ? new String[] {array.toString()} : new String[] {obj.toString()};
	}

	@Override
	public void process(String[] data, Socket sock) {
		if (data[0].startsWith("[")) {
			array = new JSONArray(data[0]);
		} else {
			obj = new JSONObject(data[0]);
		}
	}

	@Override
	public void processServer(String[] data, Socket sock) {
		process(data, sock);
	}
	
	public JSONObject getJSONObject() {
		return obj;
	}
	
	public JSONArray getJSONArray() {
		return array;
	}
	
	

}
