package moishd.server.common;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

public class LoggerCommon {
	private static Map<String, Logger> logs;
	
	private static LoggerCommon instance; 
	
	private LoggerCommon() {
		logs = new HashMap<String, Logger>();
	}
	
	public static LoggerCommon Get() {
		if (instance == null) {
			instance = new LoggerCommon();
		}
		
		return instance;
	}
	
	private Logger getLog(String key) {
		if (!logs.containsKey(key)) {
			logs.put(key, Logger.getLogger(key));
		}
		
		return logs.get(key);
	}
	
	public void LogError(HttpServlet servlet, String message) {
		this.getLog(servlet.getServletName()).log(Level.SEVERE, message);
	}
	
	public void LogError(HttpServlet servlet, String message, StackTraceElement[] messages) {
		StringBuilder sb = new StringBuilder(1000);
		
		sb.append(message);
		sb.append('\n');
		for (StackTraceElement ste : messages) {
			sb.append(ste.toString());
			sb.append('\n');
		}
		
		this.getLog(servlet.getServletName()).log(Level.SEVERE, sb.toString());
	}
	
	public void LogInfo(HttpServlet servlet, String message) {
		this.getLog(servlet.getServletName()).log(Level.INFO, message);
	}
	
	
}