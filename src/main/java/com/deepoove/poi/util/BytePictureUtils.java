package com.deepoove.poi.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 网络图片、本地图片、BufferedImage转化成byte数组，便于向word中插入图片byte[]数据。
 * </p>
 * <p>
 * 自定义图片：通过java代码生成BufferedImage，进而转化为byte数组
 * </p>
 * 
 * @author Sayi
 * @version 1.0.0
 */
public final class BytePictureUtils {

	private static Logger logger = LoggerFactory.getLogger(BytePictureUtils.class);

	/**
	 * 通过网络地址获取图片byte数组
	 * 
	 * @param urlPath
	 *            网络地址
	 * @return
	 */
	public static byte[] getUrlByteArray(String urlPath) {
		return toByteArray(getUrlPictureStream(urlPath));
	}

	/**
	 * 通过文件获取图片byte数组
	 * 
	 * @param res
	 *            文件
	 * @return
	 */
	public static byte[] getLocalByteArray(File res) {
		FileInputStream is = null;
		byte[] byteArray = null;
		try {
			is = new FileInputStream(res);
			byteArray = toByteArray(is);
			is.close();
		} catch (FileNotFoundException e) {
			logger.error("FileNotFound", e);
		} catch (IOException e) {
			logger.error("getLocalByteArray IO error", e);
		}
		return byteArray;
	}

	/**
	 * 通过BufferedImage获取图片byte数组
	 * 
	 * @param image
	 *            BufferedImage
	 * @return
	 */
	public static byte[] getBufferByteArray(BufferedImage image) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
		} catch (IOException e) {
			logger.error("getBufferByteArray error", e);
		}
		return os.toByteArray();
	}

	/**
	 * 流转换成byte数组
	 * 
	 * @param is
	 * @return
	 */
	public static byte[] toByteArray(InputStream is) {
		try {
			return IOUtils.toByteArray(is);
		} catch (IOException e) {
			logger.error("toByteArray error", e);
		}
		return null;
	}

	/**
	 * 通过网络地址获取BufferedImage
	 * 
	 * @param path
	 * @return
	 */
	public static BufferedImage getUrlBufferedImage(String urlPath) {
		URL url = null;
		BufferedImage bufferImage = null;
		try {
			url = new URL(urlPath);
			bufferImage = ImageIO.read(url);
			return bufferImage;
		} catch (Exception e) {
			logger.error("getUrlBufferedImage error, {}, {}", urlPath, e);
		}
		return null;

	}

	/**
	 * 通过文件获取BufferedImage
	 * 
	 * @param res
	 * @return
	 */
	public static BufferedImage getLocalBufferedImage(File res) {
		try {
			BufferedImage read = ImageIO.read(res);
			return read;
		} catch (FileNotFoundException e) {
			logger.error("FileNotFound", e);
		} catch (IOException e) {
			logger.error("getLocalBufferedImage IO error", e);
		}
		return null;
	}

	/**
	 * 加载网络图片流
	 * 
	 * @param urlPath
	 * @return
	 */
	public static InputStream getUrlPictureStream(String urlPath) {
		URL url = null;
		try {
			url = new URL(urlPath);
			return url.openConnection().getInputStream();
		} catch (IOException e) {
			logger.error("getUrlPictureStream error,{},{}", urlPath, e);
		}
		return null;

	}

	/**
	 * 新建RGB BufferedImage
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage newBufferImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		return image;
	}

}
