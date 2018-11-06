package utilities;

import entities.User;
import interfaces.UserServiceLocal;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import resources.AuthResource;

import javax.annotation.ManagedBean;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.security.Key;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * This interceptor verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@Provider
@ManagedBean
public class JWTRoleBasedFilter implements ContainerRequestFilter
{

    @Inject
    private UserServiceLocal userService;

    private static final ServerResponse ACCESS_DENIED = new ServerResponse("You don't have enough privileges to access this resource", 401, new Headers<Object>());
    private static final ServerResponse BAD_CREDENTIALS = new ServerResponse("Bad credentials", 401, new Headers<Object>());
    private static final ServerResponse BAD_TOKEN = new ServerResponse("Token invalid or exipred", 401, new Headers<Object>());
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("You need to log in to access this ressource", 403, new Headers<>());
    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<>());
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    ContainerRequestContext requestContext;
    SecurityContext securityContext;

    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        if(!method.isAnnotationPresent(PermitAll.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }
            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            this.requestContext = requestContext;
            this.securityContext = currentSecurityContext;
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null) {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }

            Key key = AuthResource.key;
            String username ;
            try {
                username = Jwts.parser().setSigningKey(key).parseClaimsJws(getJWT(authorizationHeader)).getBody().getSubject();
            } catch (JwtException e) {
                requestContext.abortWith(BAD_TOKEN);
                return;
            }

            //Split username and password tokens

            //Verifying Username and password
            User user = userService.findUser(username);
            //Verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));
                if (!rolesSet.contains(user.getRole())) {
                    requestContext.abortWith(ACCESS_DENIED);
                    return;
                } else
                    requestContext.setSecurityContext(new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return () -> username;
                        }

                        @Override
                        public boolean isUserInRole(String s) {
                            return false;
                        }

                        @Override
                        public boolean isSecure() {
                            return false;
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return null;
                        }
                    });
            }
        }
    }

    private String getJWT(String authorizationHeader) {
        String scheme =  authorizationHeader.split(" ")[0];
        if (scheme.equals(AUTHENTICATION_SCHEME))
            return authorizationHeader.split(" ")[1];
        else return null;
    }
}