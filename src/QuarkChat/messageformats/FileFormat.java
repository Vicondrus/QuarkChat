package QuarkChat.messageformats;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;

import QuarkChat.errorhandle.LogFile;

public class FileFormat implements Formats {
	/*
	 * FILE FORMAT
	 * 
	 * MARK FILE_NAME (x20) ENCRYPTION FILE_SIZE (x4) FILE_CONTENT(512)
	 */
	
	// configuration of the file
	static final int MAX_fileread = 300; // 1 KB maximum file size buffer
	public FileInputStream file = null; 
	
	// for informations send packs
	private byte[] dataPack = null;
	private byte[] fileName = new byte[20];
	
	// encryption marks
	private final byte[] encryptionsMark = QuarkChat.encryption.types.EncrSym.whatEnable();
	
	
	public FileFormat(String file_path, String file_name) throws FileNotFoundException {
		this.file = new FileInputStream(file_path);
		this.dataPack = new byte[1029];
		
		// copiem numele fisierului
		System.arraycopy(file_name.getBytes(), 0, fileName, 0, file_name.length());
	}

	@Override
	public byte[] getData() {
		try {
			this.digestInfo();
			return this.dataPack;
		} catch (IOException error) {
			LogFile.logger.log(Level.WARNING, "IOException -> File format", error);
			System.err.println("[Error] Something went wrong when reading from file! Check logs");
		}
		return null;
	}

	// O ZI PERFECTA! 
	// FRUMOASA SI ETERNA ROMANIA!
	// BEAU CU BAIETII MEI
	
	@Override
	public void digestInfo() throws IOException {	
		// allocate memory for dataPack
		this.dataPack = new byte[encryptionsMark.length + 1 + 20 + 4 + FileFormat.MAX_fileread];
				
		// add markers
		this.dataPack[0] = MARKS[1];
		
		// add the file name
		System.arraycopy(this.fileName, 0, this.dataPack, 1, 
				20);
		
		// add the message the encryption types for each message
		System.arraycopy(encryptionsMark, 0, this.dataPack, 1 + 20, 
				encryptionsMark.length);
		
		// add the file content to the byte array
		int fileRemainingSize = this.file.available()>FileFormat.MAX_fileread?FileFormat.MAX_fileread:this.file.available();
		byte[] tempData = ByteBuffer.allocate(4).putInt(fileRemainingSize).array();
		
		// copy size
		System.arraycopy(tempData, 0, this.dataPack, 1 + 20 + encryptionsMark.length, 4);

		this.file.read(this.dataPack, 1 + 20 + encryptionsMark.length + 4, fileRemainingSize);
	}
	
	@Override
	public boolean isFinish() {
		try {
			if(this.file.available() == 0)
			{
				return true;
			}
			else {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return true;
		}
	}
}
