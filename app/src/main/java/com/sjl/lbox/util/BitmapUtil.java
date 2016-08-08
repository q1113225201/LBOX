package com.sjl.lbox.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;

public class BitmapUtil {
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {   
	    // 源图片的高度和宽度   
	    final int height = options.outHeight;   
	    final int width = options.outWidth;   
	    int inSampleSize = 1;   
	    if (height > reqHeight || width > reqWidth) {   
	        // 计算出实际宽高和目标宽高的比率   
	        final int heightRatio = Math.round((float) height / (float) reqHeight);   
	        final int widthRatio = Math.round((float) width / (float) reqWidth);   
	        // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高   
	        // 一定都会大于等于目标的宽和高。   
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;   
	    }   
	    return inSampleSize;   
	} 
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {   
	    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小   
	    final BitmapFactory.Options options = new BitmapFactory.Options();   
	    options.inJustDecodeBounds = true;   
	    BitmapFactory.decodeResource(res, resId, options);   
	    // 调用上面定义的方法计算inSampleSize值   
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);   
	    // 使用获取到的inSampleSize值再次解析图片   
	    options.inJustDecodeBounds = false;   
	    return BitmapFactory.decodeResource(res, resId, options);   
	}
	
	public static Bitmap decodeSampledBitmapFromFilePath(String filePath, int reqWidth, int reqHeight) {   
	    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小   
	    final BitmapFactory.Options options = new BitmapFactory.Options();   
	    options.inJustDecodeBounds = true;
//	    InputStream is = new FileInputStream(filePath);
//	    System.out.println("is=" + is);
	    BitmapFactory.decodeFile(filePath, options); 
	    // 调用上面定义的方法计算inSampleSize值   
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);   
	    // 使用获取到的inSampleSize值再次解析图片   
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filePath, options);
	}  
	
	/**
     * 旋转图片
     * @param bitmap Bitmap
     * @param orientationDegree 旋转角度
     * @return 旋转之后的图片
     */
    public static Bitmap rotate(Bitmap bitmap, int orientationDegree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(orientationDegree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    /**
     * 保存bitmap图片
     * @param bitmap
     * @param outFile
     * @return
     * @throws IOException 
     */
    public static boolean save(Bitmap bitmap, String outFile)
            throws IOException {
        if (TextUtils.isEmpty(outFile) || bitmap == null)
            return false;
        byte[] data = bitmap2byte(bitmap);
        return save(data, outFile);
    }

    /**
     * 保存图片字节
     * @param bitmapBytes
     * @param outFile
     * @return
     * @throws IOException
     */
    public static boolean save(byte[] bitmapBytes, String outFile)
            throws IOException {
        FileOutputStream output = null;
        FileChannel channel = null;
        try {
        	new File(outFile).delete();
            FileUtil.createFile(outFile);
            output = new FileOutputStream(outFile);
            channel = output.getChannel();
            ByteBuffer buffer = ByteBuffer.wrap(bitmapBytes);
            channel.write(buffer);
            return true;
        } finally {
            IOUtil.close(channel);
            IOUtil.close(output);
        }
    }

    /**
     * 将Bitmap转化为字节数组
     * @param bitmap
     * @return byte[]
     * @throws IOException 
     */
    public static byte[] bitmap2byte(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] array = baos.toByteArray();
            baos.flush();
            return array;
        } finally {
            IOUtil.close(baos);
        }
    }
}
