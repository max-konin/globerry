package com.globerry.project.utils;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/** * * @author Artem */
/*
 * Класс для logger
 * http://www.quizful.net/post/log4j-quickstart
 */
public class Log4jInit extends HttpServlet {

@Override

public void init(){
        String logfilename = getInitParameter("logfile"); /* logfilename - получает имя файла конфигурации из пар-ра инициализации сервлета*/
        String pref ="";
        pref = getServletContext().getRealPath("/");
        /*pref – указывает на путь к файлу конфигурации*/
        PropertyConfigurator.configure(pref+logfilename);
        Logger globallog = Logger.getRootLogger();
        getServletContext().setAttribute(new String("log4"), globallog);
        getServletContext().setAttribute("logfilename", logfilename);
        globallog.info("Load-onstart-up Servlet");
    }

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException
{
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
                String pref = getServletContext().getRealPath("/");
                String logfile = (String) getServletContext().getAttribute("logfilename");
                /* TODO output your page here */
                out.println("<html><head>");
                out.println("<title>Servlet Log4jInit</title>");
                out.println("</head><body>");
                out.println("<h2>File properties Log4jInit " + pref + logfile + "</h2>");
                out.println("</body></html>");
        
        } finally 
        {
                out.close();
        }

}
}