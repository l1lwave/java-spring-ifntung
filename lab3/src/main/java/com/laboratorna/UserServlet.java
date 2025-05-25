package com.laboratorna;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        try {
            emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
            System.out.println("EntityManagerFactory initialized successfully");
        } catch (Exception e) {
            throw new ServletException("Error initializing EntityManagerFactory", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h2>Users List</h2>");

        EntityManager em = emf.createEntityManager();
        try {
            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();

            if (users.isEmpty()) {
                out.println("<p>No users found.</p>");
            } else {
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Username</th><th>Password</th></tr>");
                for (User user : users) {
                    out.println("<tr>");
                    out.println("<td>" + user.getId() + "</td>");
                    out.println("<td>" + user.getUsername() + "</td>");
                    out.println("<td>" + user.getPassword() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
        } catch (Exception e) {
            out.println("<p>Error retrieving users: " + e.getMessage() + "</p>");
        } finally {
            em.close();
        }

        out.println("<br><a href='/'>Add New User</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ServletException("Error saving user", e);
        } finally {
            em.close();
        }

        resp.sendRedirect("users");
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}