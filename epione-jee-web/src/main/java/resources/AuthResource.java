package resources;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.User;
import interfaces.UserServiceLocal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;
import services.UserService;

@Path("/auth")
public class AuthResource {

    public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @EJB
    private UserServiceLocal userService;

    @POST
    @PermitAll
    public Response auth(@FormParam("username")String username, @FormParam("password") String password) {
        User u;
        try {
            u = userService.findUser(username);
        } catch (NoResultException e){
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad credentials").build();
        }
        if (!BCrypt.checkpw(password, u.getPassword()))
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad credentials").build();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600 * 2 * 1000);
        String jws = Jwts.builder()
                .setSubject(u.getUsername())
                .setExpiration(expiration)
                .signWith(key).compact();
        return Response.status(200).entity(jws).build();
    }

}
