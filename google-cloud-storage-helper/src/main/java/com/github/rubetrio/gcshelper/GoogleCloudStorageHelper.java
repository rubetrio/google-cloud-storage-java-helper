package com.github.rubetrio.gcshelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import com.google.api.client.util.Charsets;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobSourceOption;
import com.google.cloud.storage.StorageOptions;

public class GoogleCloudStorageHelper{
	
	private final Storage storage;
	private final File gcsJsonFile;
	private final JSONObject json;
	
	public GoogleCloudStorageHelper() throws IOException, JSONException{
		gcsJsonFile = ResourceUtils.getFile("classpath:google_credential.json");		
		storage = StorageOptions.newBuilder()
			          .setCredentials(
			              ServiceAccountCredentials.fromStream(
			                  new FileInputStream(gcsJsonFile)))
			          .build()
			          .getService();
		InputStream is = new FileInputStream(gcsJsonFile);
        String jsonTxt = IOUtils.toString(is, Charsets.UTF_8);
        json = new JSONObject(jsonTxt);  
	}
	
	public void createFile(String dataBase64, String fileName, String contentType) throws JSONException, IOException {
		InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(dataBase64.getBytes(StandardCharsets.UTF_8)));
		byte[] dataByte = IOUtils.toByteArray(stream); 	 
		BlobId blobId = BlobId.of((String)json.get("project_id"), fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
		storage.create(blobInfo, dataByte);
	}

	public String getFileMediaLink(String fileName) throws FileNotFoundException, IOException, JSONException {
		BlobId blobId = BlobId.of((String)json.get("project_id"), fileName);
		Blob blob = storage.get(blobId);
		return blob.getMediaLink();
	}
	
	public byte[] getFileBytes(String fileName) throws FileNotFoundException, IOException, JSONException {
		BlobId blobId = BlobId.of((String)json.get("project_id"), fileName);
		return storage.readAllBytes(blobId, BlobSourceOption.generationMatch());
	}

}
