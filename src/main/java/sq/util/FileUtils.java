package sq.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

public class FileUtils {
	public static void fileCopy(final File in, final File out)
			throws IOException {
		if (!in.exists()) {
			return;
		}
		final FileChannel inChannel = new FileInputStream(in).getChannel();
		final FileChannel outChannel = new FileOutputStream(out).getChannel();

		try {
			// inChannel.transferTo(0, inChannel.size(), outChannel); //
			// original -- apparently has trouble copying large files on Windows
			// magic number for Windows, (64Mb - 32Kb)
			final int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			final long size = inChannel.size();
			long position = 0;
			while (position < size) {
				position += inChannel
						.transferTo(position, maxCount, outChannel);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}

	public static void download(final String url, final File file)
			throws Exception {
		final URL urlObj = new URL(url);

		final InputStream inputStream = urlObj.openStream();
		final FileOutputStream outputStream = new FileOutputStream(file);
		final byte[] read = new byte[49152];
		int count;
		while ((count = inputStream.read(read)) != -1) {
			outputStream.write(read, 0, count);
			if (count % 1000 == 0) {
			}
		}
		inputStream.close();
		outputStream.close();
	}

	public static InputStream getFileInputStream(String imagePath)
			throws IOException {
		InputStream netFileInputStream;
		final URL url = new URL(imagePath);
		final URLConnection urlConn = url.openConnection();
		netFileInputStream = urlConn.getInputStream();
		return netFileInputStream;
	}

	public static boolean isImageExists(final String imagePath) {
		InputStream netFileInputStream = null;
		try {
			netFileInputStream = getFileInputStream(imagePath);
			if (null != netFileInputStream) {
				return true;
			} else {
				return false;
			}
		} catch (final IOException e) {
			return false;
		} finally {
			try {
				if (netFileInputStream != null)
					netFileInputStream.close();
			} catch (final IOException e) {
			}
		}
	}

//	public static void main(final String[] args) {
//		System.out
//				.println(isImageExists("http://localhost:8081/res/item/item_1179.jpg"));
//	}

	public static void writeFile(InputStream inputStream, File file) {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			final byte[] read = new byte[49152];
			int count;
			while ((count = inputStream.read(read)) != -1) {
				outputStream.write(read, 0, count);
				if (count % 1000 == 0) {
				}
			}
			inputStream.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
