package com.laboratorna;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException
    {
        HttpSession session = request.getSession();
        session.setAttribute("username", "JohnDoe");

        String username = (String) session.getAttribute("username");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Hello, " + username + "</h1>");
    }

    private static void helloFunc(){
        String heloworld = "Hello, World!";
        char[] chars = heloworld.toCharArray();
        Stream.of(chars).forEach(System.out::println);
    
    }

}
