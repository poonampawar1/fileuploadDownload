package com.example.fileUpload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class FileUploadDao {
	
	@Inject
	ConnectionProvider connection;
	
	public String uploadFile(FileUploadModel model)
	{
		Connection con=connection.getcon();
		System.out.println("welcome connection" +con);
		try {
			PreparedStatement psta=con.prepareStatement("insert into fileUpload.dbo.filesave(mediaId,mediaName,mediaType,mediaInputStream) values(?,?,?,?)");
		  
			
			String mediaName=model.getMediaName();
			String type=model.getMediaType();
			byte[] data=model.getMediaInputStream();
			
			psta.setString(1,model.getMediaId());
			psta.setString(2,mediaName);
			psta.setString(3,type);
			psta.setBytes(4, data);
			
			psta.executeUpdate();
			return "success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public FileUploadModel downloadFile(String mediaId) throws IOException
	{
		Connection con=connection.getcon();
		try {
			PreparedStatement psta=con.prepareStatement("select * from fileUpload.dbo.filesave where mediaId=?");
			psta.setString(1,mediaId);
			ResultSet result=psta.executeQuery();
			while(result.next()) {
			FileUploadModel model=buildResultSet(result);
			return model;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	private FileUploadModel buildResultSet(ResultSet result) throws SQLException, IOException {
		// TODO Auto-generated method stub
		FileUploadModel model=new FileUploadModel();
		String mediaId=result.getString(1);
		String mediaName=result.getString(2);
		String type=result.getString(3);
		byte[] data=result.getBytes(4);
		
		model.setMediaId(mediaId);
		model.setMediaName(mediaName);
		model.setMediaType(type);
		model.setMediaInputStream(data);
		
		String extension=FilenameUtils.getExtension(mediaName);
		
		String newFileName=model.getMediaName();
		
		String newfileNameWithExtension=newFileName+ "." +extension;
		
		InputStream targetStream=new ByteArrayInputStream(data);
		
		String home=System.getProperty("user.home");
		
		File withfile=new File(home+  "/Downloads/" +newfileNameWithExtension);
		
		if (mediaName == null || mediaName.isEmpty()) {
			System.out.println(mediaName + "Doesnt have any content to show");
		}
		boolean removeAllExtensions = false;

		String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
		String filenamewithoutextension = mediaName.replaceAll(extPattern, "");

		int count = 0;

		while (withfile.exists()) {

			newfileNameWithExtension = filenamewithoutextension + "(" + (count++) + ")" + "." + extension;
			withfile = new File(home + "/Downloads/" + newfileNameWithExtension);
			System.out.println(count + ":          no of times file is being downloaded");
			System.out.println(filenamewithoutextension + "    :new file without extensoin");
			System.out.println(newfileNameWithExtension + "    :with extensoin");
		}

		FileOutputStream fos = new FileOutputStream(withfile);
		IOUtils.copy(targetStream, fos);
		fos.write(data);
		fos.close();
		FileInputStream fis = null;
		FileUploadModel vendorMedia = null;
		fis = new FileInputStream(withfile);
		byte[] byteArrayOfFile = IOUtils.toByteArray(fis);
		vendorMedia = new FileUploadModel();
		vendorMedia.setMediaName(mediaName);
		System.out.println("download file " + vendorMedia);
		return vendorMedia;

		
	}
}
