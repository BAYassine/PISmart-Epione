package interfaces;

import java.util.List;

import javax.ejb.Remote;
import javax.json.JsonObject;

import entities.Appointment;
import entities.NotificationApp;
import entities.NotificationApp.Confiramtion;

@Remote
public interface NotificationAppServiceRemote {
	public JsonObject addNotification(Appointment app);
	public List<NotificationApp> getNotificationByDate(String date);
	public List<NotificationApp> getAllNotification();
	public JsonObject Confirmation(int idP);

}
