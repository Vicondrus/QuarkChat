package QuarkChat.messageformats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import QuarkChat.errorhandle.LogFile;

public class FileFormatR implements Reassamble {
	// where to save the input file
	FileOutputStream fisier = null;
	
	private final byte[] encryptionsMark = QuarkChat.encryption.types.EncrSym.whatEnable();

	public FileFormatR(byte[] InputData) {
		// get file name
		byte[] fileNameByte = new byte[20];
		System.arraycopy(InputData, 1, fileNameByte, 0, 20);
		
		// file manipulation
		File fisier = new File("test_file");
		if(!fisier.exists()) {
			try {
				fisier.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			this.fisier = new FileOutputStream(fisier, true);
		} catch (FileNotFoundException error) {
			LogFile.logger.log(Level.WARNING, "FileFormatR -> constructor", error);
		}
		
		// data size
		int startPos = 1 + 20 + encryptionsMark.length;
		int messageSize = InputData[startPos] << 24 | (InputData[startPos + 1] & 0xFF) << 16 | (InputData[startPos + 2] & 0xFF) << 8 | (InputData[startPos + 3] & 0xFF);
		
		// prepare input data to be written
		byte[] data = new byte[300];
		System.arraycopy(InputData, 1 + 20 + 4, data, 0, messageSize);
				
		try {
			this.fisier.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.fisier.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] indigest() {
		// TODO Auto-generated method stub
		return null;
	}
}
