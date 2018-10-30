package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import entities.Report;
import interfaces.ReportServiceLocal;

@Path("/files")
public class RestFiles {
	
	@EJB
	ReportServiceLocal rs;
	
	   @POST
	    @Path("/upload")
	    @Consumes(MediaType.MULTIPART_FORM_DATA)
	   @PermitAll
	    public Response uploadFile(MultipartFormDataInput input , @QueryParam("idReport") int idReport) throws IOException {
		   Report r = rs.getReportById(idReport);
	        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
	  
	        // Get file data to save
	        List<InputPart> inputParts = uploadForm.get("attachment");
	  
	        for (InputPart inputPart : inputParts) {
	            try {
	  
	                MultivaluedMap<String, String> header = inputPart.getHeaders();
	                String fileName = getFileName(header);
	              
	                // convert the uploaded file to inputstream
	                InputStream inputStream = inputPart.getBody(InputStream.class,
	                        null);
	  
	                byte[] bytes = IOUtils.toByteArray(inputStream);
	 
	        String path = System.getProperty("user.home") + File.separator + "uploads";
	        File customDir = new File(path);
	 
	        if (!customDir.exists()) {
	            customDir.mkdir();
	            }
	                fileName = customDir.getCanonicalPath()  + File.separator + fileName;
	                writeFile(bytes, fileName);
	                r.setPathFile(fileName);
	                System.out.println("Report updated");
	                rs.updateReport(r);
	                   
	                return Response.status(200).entity("Uploaded file name : " + fileName)
	                        .build();
	  
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	    }
	  
	    private String getFileName(MultivaluedMap<String, String> header) {
	  
	        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
	  
	        for (String filename : contentDisposition) {
	            if ((filename.trim().startsWith("filename"))) {
	  
	                String[] name = filename.split("=");
	  
	                String finalFileName = name[1].trim().replaceAll("\"", "");
	                return finalFileName;
	            }
	        }
	        return "unknown";
	    }
	  
	    // Utility method
	    private void writeFile(byte[] content, String filename) throws IOException {
	        File file = new File(filename);
	    System.out.println("----->"+filename);
	        if (!file.exists()) {
	            file.createNewFile();
	        }
	        FileOutputStream fop = new FileOutputStream(file);
	        fop.write(content);
	        fop.flush();
	        fop.close();
	    }
	    
	    /*DOWNLOAD à tester */
	    @GET
	    @Path("/jar")
	    @Produces(MediaType.APPLICATION_OCTET_STREAM)
	    @PermitAll
	    public Response downloadFile() {
	        File file = new File("C:/Users/Taha Driss/uploads/classes.jar");
	        ResponseBuilder response = Response.ok((Object) file);
	        response.header("Content-Disposition", "attachment;filename=classes.jar");
	        return response.build();
	    }

}
