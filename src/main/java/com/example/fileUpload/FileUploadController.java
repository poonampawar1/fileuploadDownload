package com.example.fileUpload;

import java.io.IOException;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;

@Controller
public class FileUploadController {
	
	public static final String CONTENT_DISPOSITION_VALUE = "attachment; filename=";
	
	@Inject
	FileUploadService upload;
	
	
	
	@Post(uri = "/uploadDoc", consumes = { MediaType.MULTIPART_FORM_DATA })
	public String uploadFile(CompletedFileUpload file) throws IOException
	{
		String media=upload.uploadFile(file);
		return media;
	}
	
	@Get(uri = "/download/{mediaId}", produces = MediaType.APPLICATION_OCTET_STREAM)
	public HttpResponse<FileUploadModel> downloadFile(@PathVariable String mediaId) throws IOException
	{
		FileUploadModel model=this.upload.download(mediaId);
		
		return HttpResponse.ok().body(model).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*").header(
				HttpHeaders.CONTENT_DISPOSITION, FileUploadController.CONTENT_DISPOSITION_VALUE +model.getMediaName());
	}

}
