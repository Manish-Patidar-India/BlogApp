package com.manishblogapp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public  final class  ImageOperation {

	public static  String uploadImage(MultipartFile file,String nameToBeSave) throws IOException {

		
		File f = new File(AppConstant.POST_IMGFOLDER);
		if (!f.exists()) {

			f.mkdir();
		}
		String fullpath = AppConstant.POST_IMGFOLDER + File.separator + nameToBeSave;

		Files.copy(file.getInputStream(), Paths.get(fullpath), StandardCopyOption.REPLACE_EXISTING);

		return nameToBeSave;
	}
	
	public static InputStream downloadImage(String imageName) throws FileNotFoundException {
		
		
		String path=AppConstant.POST_IMGFOLDER+File.separator+imageName;
		
		InputStream inputstream=new FileInputStream(path);
		
		return inputstream;
		
		
		
		
		
	}

}
