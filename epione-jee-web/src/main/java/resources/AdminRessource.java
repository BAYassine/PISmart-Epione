package resources;

import entities.User;
import interfaces.UserServiceLocal;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("admin")
public class AdminRessource {

    @EJB
    private UserServiceLocal userService;

//    @GET
//    @Produces("application/json")
//    @RolesAllowed("ROLE_ADMIN")
//    public Response dashboard(){
//        long connectedUsers = userService.todayUsers();
//        JSONObject result = new JSONObject();
//        result.put("Users connected today", connectedUsers);
//        result.put("Registred users this week", userService.registeredSince("22/10/2018"));
//        result.put("New subscribers per month", userService.subscrtionsPerMonth());
//        return Response.status(200).entity(result).build();
//    }

    @GET
    @PermitAll
    @Produces("application/pdf")
    public Response generateReport() {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        PDFont courierBoldFont = PDType1Font.COURIER_BOLD;
        int fontSize = 12;

        PDPageContentStream contentStream;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 40);
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Epione");
            contentStream.endText();
            contentStream.setFont(PDType1Font.COURIER, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(400, 736);
            contentStream.showText("96bis Boulevard Raspail");
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(400, 724);
            contentStream.showText("75006 Paris, FRANCE");
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(400, 712);
            contentStream.showText("contact@deepor.ai");
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(400, 700);
            contentStream.showText("Phone: +33.6.58.66.32.63");
            contentStream.endText();
            addLine(contentStream,"_______________________________________________________________________",50, 598);
            addLine(contentStream,"|    |             |                     |          |          |       |",47, 588);
            contentStream.setFont(PDType1Font.COURIER_BOLD, 12);
            addLine(contentStream,"  ID     Username           Email           Sign up   Sign in     Role",47, 580);
            contentStream.setFont(PDType1Font.COURIER, 12);
            addLine(contentStream,"|    |             |                     |          |          |       |",47, 580);
            addLine(contentStream,"|    |             |                     |          |          |       |",47, 574);
            addLine(contentStream,"_______________________________________________________________________",50, 576);
            contentStream.setFont(PDType1Font.COURIER, 10);
            List<User> users = userService.latestRegistrations(20);
            int ypos = 560;
            for(User u : users){
                contentStream.setFont(PDType1Font.COURIER, 12);
                addLine(contentStream,"|    |             |                     |          |          |       |",47, ypos+6);
                addLine(contentStream,"|    |             |                     |          |          |       |",47, ypos);
                addLine(contentStream,"|    |             |                     |          |          |       |",47, ypos-6);
                contentStream.setFont(PDType1Font.COURIER, 10);
                addLine(contentStream,dataLine(u), 50, ypos);
                ypos -= 20;
            }
            contentStream.setFont(PDType1Font.COURIER, 12);
            addLine(contentStream,"_______________________________________________________________________",50, ypos+16);
            addLine(contentStream,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),450, 20);
            contentStream.close();
            document.save(output);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response.ResponseBuilder responseBuilder = Response.ok(output.toByteArray());
        responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "filename=test.pdf");
        return responseBuilder.build();
    }

    private String dataLine(User u) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String userRole = u.getRole().equals( User.Roles.ROLE_DOCTOR )? "Doctor" : "Patient";
        String email = u.getEmail().length() < 22 ? u.getEmail() : u.getEmail().substring(0,22)+"..";
        return "  " + u.getId() + calculateSpaces(u.getId()+"", 6) +
                u.getUsername() + calculateSpaces(u.getUsername() , 16) +
                email + calculateSpaces(email , 26) +
                format.format(u.getRegistred_at()) + calculateSpaces(format.format(u.getRegistred_at()) , 14) +
                format.format(u.getLast_login()) + calculateSpaces(format.format(u.getLast_login()) , 13) +
                userRole ;
    }

    private void addLine(PDPageContentStream cs, String text, float x, float y) throws IOException{
        cs.beginText();
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    private String calculateSpaces(String s, int need){
        String spaces = "";
        for (int i = 0; i < need-s.length();i++)
            spaces+=" ";
        return spaces;
    }

}
