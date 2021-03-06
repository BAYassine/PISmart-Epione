package utils;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import entities.ChatMessage;
import entities.Message;;
public class MessageEncoder implements Encoder.Text<ChatMessage> {

    @Override
    public String encode(ChatMessage message) throws EncodeException {
        return Json.createObjectBuilder()
                       .add("message", message.getContent())
                       .add("doctorName", message.getDoctorName())
                       .add("patientName", message.getPatientName())
                       .add("isPatient", message.getIsPatient())
                       .build().toString();
    }

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

}