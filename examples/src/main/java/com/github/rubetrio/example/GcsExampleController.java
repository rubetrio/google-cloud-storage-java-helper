package com.github.rubetrio.example;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.rubetrio.gcshelper.GoogleCloudStorageHelper;

@RestController
public class GcsExampleController {

	@RequestMapping("/home")
	public String index() {
		return "Greetings from ShrewdTech!";
	}

	@RequestMapping(value = "/get-file", method = RequestMethod.GET)
	public String getFile(@RequestParam String fileName) throws IOException {

		try {
			
			GoogleCloudStorageHelper googleCloudStorageHelper = new GoogleCloudStorageHelper();
			String mediaLink = googleCloudStorageHelper.getFileMediaLink(fileName);
			return "Success: " + mediaLink;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return "Failed - json error";
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed - io exception";
		}
	}
	

	@RequestMapping(value = "/create-file", method = RequestMethod.POST)
	public String createFile(@RequestParam String dataBase64) throws IOException {
		
		try {
			
			GoogleCloudStorageHelper googleCloudStorageHelper = new GoogleCloudStorageHelper();
			googleCloudStorageHelper.createFile(dataBase64, "testing_image_name", "image/png");
			
		} catch (JSONException e) {
			e.printStackTrace();
			return "Failed - json error";
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed - io exception";
		}

		return "Success";
	}

}