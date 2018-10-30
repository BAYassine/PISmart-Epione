package utilities;

import entities.User;
import interfaces.UserServiceLocal;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;

import javax.annotation.ManagedBean;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * This interceptor verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@Provider
@ManagedBean
public class RoleBasedFilter //implements ContainerRequestFilter
{

//    @Inject
//    private UserServiceLocal userService;
//
//    private static final ServerResponse ACCESS_DENIED = new ServerResponse("You don't have enough privileges to access this resource", 401, new Headers<Object>());
//    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("You need to log in to access this ressource", 403, new Headers<>());
//    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<>());
//    private static final String AUTHENTICATION_SCHEME = "Basic";
//
//    ContainerRequestContext requestContext;
//    SecurityContext securityContext;
//
//    @Override
//    public void filter(ContainerRequestContext requestContext)
//    {
//        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
//        Method method = methodInvoker.getMethod();
//        if(!method.isAnnotationPresent(PermitAll.class))
//        {
//            if(method.isAnnotationPresent(DenyAll.class))
//            {
//                requestContext.abortWith(ACCESS_FORBIDDEN);
//                return;
//            }
//            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
//            this.requestContext = requestContext;
//            this.securityContext = currentSecurityContext;
//            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//            if (!isTokenBasedAuthentication(authorizationHeader)) {
//                requestContext.abortWith(ACCESS_DENIED);
//                return;
//            }
//
//            //Get encoded username and password
//            final String encodedUserPassword = authorizationHeader.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
//
//            //Decode username and password
//            String usernameAndPassword = null;
//            try {
//                usernameAndPassword = new String(Base64.decode(encodedUserPassword));
//            } catch (IOException e) {
//                requestContext.abortWith(SERVER_ERROR);
//                return;
//            }
//
//            //Split username and password tokens
//            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
//            final String username = tokenizer.nextToken();
//            final String password = tokenizer.nextToken();
//
//            //Verifying Username and password
//            User user = userService.findUser(username);
//            if(user == null || !password.equals(user.getPassword())){
//                requestContext.abortWith(ACCESS_FORBIDDEN);
//                return;
//            }
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//            if(user.getLast_login() != null){
//                String lastLogin = formatter.format(user.getLast_login());
//                if(!formatter.format(new Date()).equals(lastLogin)) {
//                    userService.updateLoginDate(user);
//                }
//            }
//            else userService.updateLoginDate(user);
//            //Verify user access
//            if(method.isAnnotationPresent(RolesAllowed.class))
//            {
//                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
//                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));
//                if( ! rolesSet.contains(user.getRole()))
//                {
//                    requestContext.abortWith(ACCESS_DENIED);
//                    return;
//                }else
//                    requestContext.setSecurityContext(new SecurityContext() {
//                        @Override
//                        public Principal getUserPrincipal() {
//                            return () -> username;
//                        }
//
//                        @Override
//                        public boolean isUserInRole(String s) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean isSecure() {
//                            return false;
//                        }
//
//                        @Override
//                        public String getAuthenticationScheme() {
//                            return null;
//                        }
//                    });
//            }
//        }
//    }
//
//    private boolean isTokenBasedAuthentication(String authorizationHeader) {
//        return authorizationHeader != null
//                && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
//    }
}