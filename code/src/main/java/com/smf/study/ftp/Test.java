package com.smf.study.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FTPClient ftp = new FTPClient();
		 try {
			FTPFile[] ftpFiles = ftp.listFiles("");
			ftpFiles[0].getTimestamp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
