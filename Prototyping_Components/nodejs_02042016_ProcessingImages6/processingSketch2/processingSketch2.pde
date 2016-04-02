import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;

HttpClient client = new DefaultHttpClient();
HttpPost method = new HttpPost("http://localhost:3000/customReq");


void setup() {
  size(320, 480);
  background(255);
  fill(0);
  textAlign(CENTER);
  
  try {
    println("Sending POST request to server - " + method.getRequestLine());
    method.addHeader("Content-Type", "application/x-www-form-urlencoded");
    method.addHeader("name","Laura");
    println("Sending POST request to server - " + method.getRequestLine());
    HttpResponse response = client.execute(method);
    HttpEntity entity = response.getEntity();
    String responseText = EntityUtils.toString(entity);
    
    text(responseText, width/2, height/2);
    client.getConnectionManager().shutdown();
  } 
  catch(IOException e) {
    println("Unable to send request - " + e);
  }
}