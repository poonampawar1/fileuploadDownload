package com.example.fileUpload;

public class FileUploadModel {

	private String mediaId;
	
	private String mediaName;
	
	private String mediaType;
	
	private byte[] mediaInputStream;
	


	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public byte[] getMediaInputStream() {
		return mediaInputStream;
	}

	public void setMediaInputStream(byte[] mediaInputStream) {
		this.mediaInputStream = mediaInputStream;
	}




	public FileUploadModel(String mediaId, String mediaName, String mediaType, byte[] mediaInputStream) {
		super();
		this.mediaId = mediaId;
		this.mediaName = mediaName;
		this.mediaType = mediaType;
		this.mediaInputStream = mediaInputStream;
	
	}

	public FileUploadModel() {
		super();
	}
	
	
}
