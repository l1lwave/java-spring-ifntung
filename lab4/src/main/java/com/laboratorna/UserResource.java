package com.laboratorna;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    public List<User> getUsers() {
        return userDAO.getAllUsers();
    }

    @POST
    public Response createUser(User user) {
        User created = userDAO.createUser(user);
        return Response.ok(created).build();
    }
}
