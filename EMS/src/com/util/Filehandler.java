package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Filehandler {
	
	public static void createFolder(String productionId){
		
		File file =new File("\\\\wangchenyi\\designDrawing\\".concat(productionId));
		
		if (!file.exists() && !file.isDirectory()) {
			
			file.mkdir();
		}
	}

	// 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    
    /**
     * 删除文件夹下的所有文件
     * @param oldPath
     */
    public void deleteFile(File oldPath) {
    	
    	if (oldPath.isDirectory()) {
    		
    		File[] files = oldPath.listFiles();
    		for (File file : files) {
    			deleteFile(file);
    		}
    	}else{
    		oldPath.delete();
    	}
    }
}
