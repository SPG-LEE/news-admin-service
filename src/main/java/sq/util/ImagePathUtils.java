package sq.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import sq.base.BaseEntity;
import sq.news.admin.constants.SysConstants;

public class ImagePathUtils {

	private static String fileDir;

	{
		// this.IMAGE_DOMAIN = domain;
		// String root = resource.getContextRoot();
		String root = "";
		if (!root.endsWith(SysConstants.FILE_SEPERATOR)) {
			root += SysConstants.FILE_SEPERATOR;
		}
		fileDir = root;
		File fileDirFile = new File(fileDir);
		if (!fileDirFile.exists()) {
			try {
				FileUtils.forceMkdir(fileDirFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!fileDir.startsWith(SysConstants.FILE_SEPERATOR)) {
			fileDir = SysConstants.FILE_SEPERATOR + fileDir;
		}
	}

	private static String constructPath(String dir, String seperator,
			String fileName) {
		if (!dir.startsWith(SysConstants.FILE_SEPERATOR)) {
			dir = SysConstants.FILE_SEPERATOR + dir;
		}
		StringBuilder sb = new StringBuilder(dir);

		sb.append(seperator);
		sb.append(fileName);
		if (fileName != null && fileName.indexOf('.') < 0) {

			sb.append(SysConstants.PICTURE_EXT_NAME);
		}

		return sb.toString();
	}

	public static File getFile(String fileName) {
		// System.out.println("fileDir=" + fileDir);
		// System.out.println("fileName=" + fileName);
		String name = fileName;
		if (fileName.startsWith("http")) {
			fileName = fileName.replace("http://", "").replace("https://", "");
			int lastIndex = fileName.indexOf(SysConstants.FILE_SEPERATOR);
			if (lastIndex >= 0) {
				name = fileName.substring(lastIndex + 1);
			}
		}
		String filePath = constructPath(fileDir, SysConstants.FILE_SEPERATOR,
				name);
		// System.out.println(name);
		File file = new File(filePath);
		try {
			FileUtils.forceMkdir(file.getParentFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	// 购物车

	public static File getSmallFile(BaseEntity entity) {
		return getFile(getSmallPath(entity));
	}

	public static String getSmallPath(BaseEntity entity) {
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR, name + "_" + entity.getId()
						+ SysConstants.SMALL_EXT);
		// return (productMode ? IMAGE_DOMAIN : "") + path;
		return path;

	}

	// 详情

	public static File getFile(BaseEntity entity) {
		return getFile(getPath(entity));
	}

	public static String getPath(BaseEntity entity) {
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR, name + "_" + entity.getId());
		return path;
	}

	// 列表

	public static File getMiddleFile(BaseEntity entity) {
		return getFile(getMiddlePath(entity));
	}

	public static String getMiddlePath(BaseEntity entity) {
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR, name + "_" + entity.getId()
						+ SysConstants.MIDDLE_EXT);
		// return (productMode ? IMAGE_DOMAIN : "") + path;
		return path;
	}

	public static File getTempFile(BaseEntity entity) {
		return getFile(getTempPath(entity));
	}

	public static File getTempFile(String fileName) {
		return getFile(fileName);
	}

	public static File getTempFile(BaseEntity entity, String ext) {
		return getFile(getTempPath(entity, ext));
	}

	public static String getTempPath(BaseEntity entity, String ext) {
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR + "temp/",
				name + "_" + entity.getId() + ext);
		return path;
	}

	public static String getTempPath(BaseEntity entity) {
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR + "temp/",
				name + "_" + entity.getId());
		return path;
	}

	public static File getTempSmallFile(BaseEntity entity) {
		return getFile(getTempSmallPath(entity));
	}

	public static File getTempSmallFile(String fileName) {
		return getFile(fileName);
	}

	public static File getTempSmallFile(BaseEntity entity, String ext) {
		return getFile(getTempSmallPath(entity, ext));
	}

	public static String getTempSmallPath(BaseEntity entity, String ext) {
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR + "temp/",
				name + "_" + entity.getId() + SysConstants.SMALL_EXT + ext);
		return path;
	}

	public static String getTempSmallPath(BaseEntity entity) {
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR + "temp/",
				name + "_" + entity.getId() + SysConstants.SMALL_EXT);
		return path;
	}

	// 大图

	public static File getBigFile(BaseEntity entity) {
		return getFile(getBigPath(entity));
	}

	public static String getBigPath(BaseEntity entity) {
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR, name + "_" + entity.getId()
						+ SysConstants.BIG_EXT);
		// return (productMode ? IMAGE_DOMAIN : "") + path;
		return path;
	}

	public static File getFile(BaseEntity entity, String ext) {
		if (ext == null || ext.trim().isEmpty()) {
			return getFile(getPath(entity));
		}
		return getFile(getEntityPath(entity, ext));
	}

	public static String getEntityPath(BaseEntity entity, String ext) {
		if (ext == null || ext.trim().isEmpty()) {
			return getPath(entity);
		}
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR, name + "_" + entity.getId() + ext);
		// return (productMode ? IMAGE_DOMAIN : "") + path;
		return path;
	}

	// public static void copyImage(BaseEntity src, BaseEntity dest, String ext)
	// {
	// try {
	// if (ext == null) {
	// FileUtils.copyFile(getFile(src), getFile(dest));
	// } else {
	// FileUtils.copyFile(getFile(src, ext), getFile(dest, ext));
	// }
	// } catch (IOException e) {
	// String ss;
	// if (ext == null) {
	// ss = IMAGE_DOMAIN + getPath(src);
	// } else {
	// ss = IMAGE_DOMAIN + getEntityPath(src, ext);
	// }
	// System.out.println("路径为：" + ss + "的文件不存在");
	// // e.printStackTrace();
	// }
	//
	// }
	//
	// public static void copyImage(BaseEntity src, BaseEntity dest) {
	// copyImage(src, dest, null);
	// }

	// public static void copySmallImage(BaseEntity src, BaseEntity dest,
	// String ext) {
	// try {
	// if (ext == null) {
	// FileUtils.copyFile(getSmallFile(src), getSmallFile(dest));
	// } else {
	// FileUtils.copyFile(getSmallFile(src, ext),
	// getSmallFile(dest, ext));
	// }
	// } catch (IOException e) {
	// String ss;
	// if (ext == null) {
	// ss = IMAGE_DOMAIN + getSmallPath(src);
	// } else {
	// ss = IMAGE_DOMAIN + getSmallPath(src, ext);
	// }
	// System.out.println("路径为：" + ss + "的文件不存在");
	// // e.printStackTrace();
	// }
	//
	// }
	//
	// public static void copySmallImage(BaseEntity src, BaseEntity dest) {
	// copySmallImage(src, dest, null);
	// }

	// @Inject
	// private UploadManager uploadManager;
	// @Inject
	// private Auth auth;
	//
	//
	// public static void upload(UploadedFile file, BaseEntity entity) {
	// upload(file, entity, null);
	// }
	//
	//
	// public static void upload(UploadedFile file, BaseEntity entity, String
	// ext) {
	// String name = entity.getClassName();
	// StringBuilder keyBuilder = new StringBuilder(name);
	// keyBuilder.append("_");
	// keyBuilder.append(entity.getId());
	// if (ext != null && !ext.trim().isEmpty()) {
	// keyBuilder.append("_");
	// keyBuilder.append(ext);
	// }
	// keyBuilder.append(SysConstants.PICTURE_EXT_NAME);
	// String key = keyBuilder.toString();
	// try {
	// uploadManager.put(IOUtils.toByteArray(file.getStream()), key,
	// auth.uploadToken(name));
	// } catch (QiniuException e) {
	// Response r = e.response;
	// // 请求失败时简单状态信息
	// System.out.println(r.toString());
	// try {
	// // 响应的文本信息
	// System.out.println(r.bodyString());
	// } catch (QiniuException e1) {
	// // ignore
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	//
	// public static String getPath(BaseEntity entity, String ext, int width,
	// int
	// height) {
	// StringBuilder sb = new StringBuilder(SysConstants.QINIU_DOMAIN);
	//
	// return null;
	// }
	//
	//
	// public static String getPath(BaseEntity entity, int width, int height) {
	// return null;
	// }

	public static File getSmallFile(BaseEntity entity, String ext) {
		return getFile(getSmallPath(entity, ext));
	}

	public static String getSmallPath(BaseEntity entity, String ext) {
		String name = entity.getClassName();

		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR, name + "_" + entity.getId()
						+ SysConstants.SMALL_EXT + ext);
		// return (productMode ? IMAGE_DOMAIN : "") + path;
		return path;
	}

	public static String getImage(BaseEntity entity, int maxSideWidth) {
		return getImage(entity, null, maxSideWidth, 70);
	}

	public static String getImage(BaseEntity entity, String ext,
			int maxSideWidth) {
		return getImage(entity, ext, maxSideWidth, 70);
	}

	public static String getImage(BaseEntity entity, String ext,
			int maxSideWidth, int quality) {
		// String name = entity.getClassName();
		// String fileName = name + "_" + entity.getId();
		// if (ext != null) {
		// fileName += ext;
		// }
		// String path = constructPath(SysConstants.DIR_PATH + name,
		// SysConstants.FILE_SEPERATOR, fileName);
		// System.out.println(productModel);
		// if (productMode) {
		// if (maxSideWidth <= SysConstants.ZERO_WIDTH) {
		// return OSS_DOMAIN + path + "@" + quality + "q";
		// } else {
		// return OSS_DOMAIN + path + "@0e_0o_0l_" + maxSideWidth + "w_" +
		// maxSideWidth + "h_" + quality + "q";
		// }
		// }
		return getEntityPath(entity, ext);

	}

	public static String getPath(BaseEntity entity, String ext) {
		String name = entity.getClassName();
		String path = constructPath(SysConstants.DIR_PATH + name,
				SysConstants.FILE_SEPERATOR, name + "_" + entity.getId() + ext);
		return path;
	}

	public static String getOSSPath(BaseEntity entity) {
		String name = entity.getClassName();
		String path = SysConstants.FILE_SEPERATOR + name + "_"
				+ new Date().getTime() + ".jpg";
		return path;
	}

	public static String getOSSPath(String dir, String fileName) {
		String path = dir + fileName;
		return path;
	}

	public static String getOSSDirPath(BaseEntity entity) {
		String name = entity.getClassName();
		String path = name + "/";
		return path;
	}

	public static String getOSSDirPath(String dir) {
		String path = dir + "/";
		return path;
	}

	public static String getOSSFileName(BaseEntity entity) {
		String name = entity.getClassName();
		String path = name + "_" + new Date().getTime() + ".jpg";
		return path;
	}

	public static String getOSSFileName(BaseEntity entity, String ext) {
		if (ext == null || ext.trim().isEmpty()) {
			return getOSSFileName(entity);
		}
		String name = entity.getClassName();
		String path = name + "_" + new Date().getTime() + ext;
		return path;
	}

	public static String getOSSFileName(String string) {
		return getOSSFileName(string, ".jpg");
	}

	public static String getOSSFileName(String string, String ext) {

		String path = string + "_" + new Date().getTime() + ext;
		return path;
	}

	public static void deleteFile(BaseEntity entity) {
		File file = getFile(entity);
		if (file.exists()) {
			FileUtils.deleteQuietly(file);
		}
		File middleFile = getMiddleFile(entity);
		if (middleFile.exists()) {
			FileUtils.deleteQuietly(middleFile);
		}
		File smallFile = getSmallFile(entity);
		if (smallFile.exists()) {
			FileUtils.deleteQuietly(smallFile);
		}
	}

	// public static String getImageServerPath() {
	// return IMAGE_DOMAIN;
	// }

}
