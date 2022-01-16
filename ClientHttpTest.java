import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class ClientHttpTest {


	    public static void main(String... args) throws IOException {
	    	
	        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
	            HttpPost httpPost = new HttpPost("http://localhost:8801");
	            httpPost.setEntity(new StringEntity("Hello, World"));

	            System.out.println("Executing request " + httpPost.getRequestLine());

	      
	        }
	    }
	

}
