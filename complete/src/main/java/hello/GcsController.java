package hello;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@RestController
public class GcsController {
	
	
  @Value("gs://future-surge-174306/my-file.txt")
  private Resource gcsFile;

  @RequestMapping(value = "/get-file", method = RequestMethod.GET)
  public String readGcsFile() throws IOException {
    return StreamUtils.copyToString(
        gcsFile.getInputStream(),
        Charset.defaultCharset()) + "\n";
  }
  
  @RequestMapping(value = "/create-file", method = RequestMethod.POST)
  public String createGcsFile(@RequestParam String imageData) throws IOException {
	  
	  String SERVICE_ACCOUNT_JSON_PATH = "/usr/local/tomcat/conf/gcp_spring_poc.json";

	  System.out.println("****testing: "+imageData);
	  
	  Storage storage =
	      StorageOptions.newBuilder()
	          .setCredentials(
	              ServiceAccountCredentials.fromStream(
	                  new FileInputStream(SERVICE_ACCOUNT_JSON_PATH)))
	          .build()
	          .getService();

	  //byte[] imageByte = DatatypeConverter.parseBase64Binary(imageData);	
	  
	  InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageData.getBytes(StandardCharsets.UTF_8)));
	 
	  byte[] imgByte = IOUtils.toByteArray(stream); 
	  BlobId blobId = BlobId.of("future-surge-174306", "testing_image");
	  BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/png").build();
	  storage.create(blobInfo, imgByte);

//	  BlobId blobId = BlobId.of("future-surge-174306", "tesing_text");
//	  BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
//	  storage.create(blobInfo, "Hello, Cloud Storage!".getBytes(UTF_8));
	  
	  return "Yes!";
  }
  
  public static void main(String args[]) {
	  String abc = "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";
	  InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(abc.getBytes(StandardCharsets.UTF_8)));
	  System.out.println("****testing: "+stream);
  }
  
  
  @RequestMapping(value = "/create-file-part", method = RequestMethod.POST)
  public String createGcsFilePart(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	  
	  String SERVICE_ACCOUNT_JSON_PATH = "/usr/local/tomcat/conf/gcp_spring_poc.json";
	  
	  Storage storage =
		      StorageOptions.newBuilder()
		          .setCredentials(
		              ServiceAccountCredentials.fromStream(
		                  new FileInputStream(SERVICE_ACCOUNT_JSON_PATH)))
		          .build()
		          .getService();

	  
	  DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
      DateTime dt = DateTime.now(DateTimeZone.UTC);
      String dtString = dt.toString(dtf);
      Part filePart = req.getPart("image");
      final String fileName = filePart.getSubmittedFileName() + dtString;

      byte[] imgByte = IOUtils.toByteArray(filePart.getInputStream()); 
      
      // the inputstream is closed by default, so we don't need to close it here
      BlobId blobId = BlobId.of("future-surge-174306", "testing_image");
      BlobInfo blobInfo =
              storage.create(
                      BlobInfo
                              .newBuilder(blobId)
                              .setContentType("image/png").build(),
                              imgByte);
	  
	  return "Yes!";
  }
  
}