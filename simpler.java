import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Main {

    public static void main(String[] args) {
        String paragraph = "This is a small paragraph. I created this paragraph for the semester exercise in Advanced Programming Techniques course. :)";

        // Sending the paragraph through an HTTP POST request
        String response = sendHttpPostRequest(paragraph);
        System.out.println("HTTP Response: " + response);

        // Publishing the received paragraph through MQTT
        publishMessageViaMqtt(response);
    }

    private static String sendingHttpPostRequest(String paragraph) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, paragraph);
        Request request = new Request.Builder()
                .url("tcp://test.mosquitto.org:1883")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();        
        try (response) {
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void publishMessageViaMqtt(String message) {
        String broker = "tcp://test.mosquitto.org:1883";
        String clientId = "course_id";

        try {
            MqttClient client = new MqttClient(broker, clientId);
            client.connect();

            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(0);

            client.publish("topic", mqttMessage);

            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
