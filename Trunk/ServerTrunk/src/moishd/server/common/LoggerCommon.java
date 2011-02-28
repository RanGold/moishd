package moishd.server.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

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
	
	public void LogError(HttpServlet servlet, String message) throws IOException {
		this.LogError(servlet, null, message);
	}
	
	public void LogError(HttpServlet servlet, HttpServletResponse response, String header, String message) throws IOException {
		this.getLog(servlet.getServletName()).log(Level.SEVERE, message);
		
		if (response != null) {
			response.addHeader(header, "");
			response.getWriter().println(servlet.getServletName() + ": no logged in user");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	public void LogError(HttpServlet servlet, HttpServletResponse response, String message) throws IOException {
		LogError(servlet, response, "Error", message);
	}
	
	public void LogError(HttpServlet servlet, String message, StackTraceElement[] messages) throws IOException {
		this.LogError(servlet, null, message, messages);
	}
	
	public void LogError(HttpServlet servlet, HttpServletResponse response, String message, StackTraceElement[] messages) throws IOException {
		StringBuilder sb = new StringBuilder(1000);
		
		sb.append(message);
		sb.append('\n');
		sb.append(messages[0].toString());
		
		this.getLog(servlet.getServletName()).log(Level.SEVERE, sb.toString());
		
		if (response != null) {
			response.addHeader("Error", "");
			response.getWriter().println(sb.toString());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	public void LogInfo(HttpServlet servlet, String message) {
		this.getLog(servlet.getServletName()).log(Level.WARNING, message);
	}
	
	public void LogInfo(String source, String message) {
		this.getLog(source).log(Level.WARNING, message);
	}
}