package resources;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
/* Test Class*/
public class client {
	public static void main(String[] args) {
		downloadUpdate();
	}
	public static void downloadUpdate() {
	    String BASE_URL="http://localhost:18080/epione-jee-web/api/demo/jar";
	 
	    Client client = ClientBuilder.newClient();
	 
	     
	    try {
	        URL website = new URL(BASE_URL);
	        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	        FileOutputStream fos = new FileOutputStream("classes.jar");
	        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	 
	    } catch ( Exception ex) {
	    ex.printStackTrace();
	    }  

}
}
