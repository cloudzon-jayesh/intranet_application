package com.cloudzon.huddle.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class GetMultipartFile implements MultipartFile {
	private final byte[] imgContent;

	public GetMultipartFile(byte[] imgContent) {
		this.imgContent = imgContent;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOriginalFilename() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return imgContent != null && imgContent.length > 0;
	}

	@Override
	public long getSize() {
		return imgContent.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return imgContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(imgContent);
	}

	@SuppressWarnings("resource")
	@Override
	public void transferTo(File destination) throws IOException,
			IllegalStateException {
		new FileOutputStream(destination).write(imgContent);
	}
}