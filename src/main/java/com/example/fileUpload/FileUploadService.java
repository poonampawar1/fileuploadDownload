package com.example.fileUpload;

import java.io.IOException;
import java.util.UUID;

import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class FileUploadService {
	
	@Inject 
	private FileUploadDao dao;

	public String uploadFile(CompletedFileUpload file) throws IOException {
		// TODO Auto-generated method stub
		String fileName=file.getFilename();
		String type=file.getContentType().toString();
		byte[] data=file.getBytes();
		String mediaId=UUID.randomUUID().toString();
		
		FileUploadModel model=new FileUploadModel();
		model.setMediaId(mediaId);
		model.setMediaName(fileName);
		model.setMediaType(type);
		model.setMediaInputStream(data);
		String uploadFile=this.dao.uploadFile(model);
		return uploadFile;
	}
	
	public FileUploadModel download(String mediaId) throws IOException
	{
		FileUploadModel model=this.dao.downloadFile(mediaId);
		return model;
	}
}