package com.liuhuiyu.core.util;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * 压缩解压工具类
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-12-08 16:30
 */
public class CompressUtil {
    public static final String ALL_FILE = "*";

    /**
     * 压缩文件或者目录
     *
     * @param baseDirName    压缩的根目录
     * @param fileName       根目录下待压缩的文件或文件夹名，
     *                       星号*表示压缩根目录下的全部文件。
     * @param targetFileName 目标ZIP文件
     */
    public static void zipFile(String baseDirName, String fileName,
                               String targetFileName) {
        Assert.assertNotNull(baseDirName, "压缩失败，根目录不能为空");
        File baseDir = new File(baseDirName);
        if (!baseDir.exists() || (!baseDir.isDirectory())) {
            System.out.println("压缩失败，根目录不存在：" + baseDirName);
            return;
        }
        String baseDirPath = baseDir.getAbsolutePath();
        //目标文件
        File targetFile = new File(targetFileName);
        try {
            //创建一个zip输出流来压缩数据并写入到zip文件
            ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(targetFile.toPath()));
            if (ALL_FILE.equals(fileName)) {
                //将baseDir目录下的所有文件压缩到ZIP
                CompressUtil.dirToZip(baseDirPath, baseDir, out);
            }
            else {
                File file = new File(baseDir, fileName);
                if (file.isFile()) {
                    CompressUtil.fileToZip(baseDirPath, file, out);
                }
                else {
                    CompressUtil.dirToZip(baseDirPath, file, out);
                }
            }
            out.close();
            System.out.println("压缩文件成功，目标文件名：" + targetFileName);
        }
        catch (IOException e) {
            throw new RuntimeException("压缩失败", e);
        }
    }

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到targetDIR目录下
     *
     * @param zipFileName       待解压缩的ZIP文件名
     * @param targetBaseDirName 目标目录
     */
    public static void upZipFile(String zipFileName, String targetBaseDirName) {
        if (!targetBaseDirName.endsWith(File.separator)) {
            targetBaseDirName += File.separator;
        }
        try {
            //根据ZIP文件创建ZipFile对象
            ZipFile zipFile = new ZipFile(zipFileName);
            ZipEntry entry;
            String entryName;
            String targetFileName;
            byte[] buffer = new byte[4096];
            int bytesRead;
            //获取ZIP文件里所有的entry
            Enumeration<? extends ZipEntry> entryElement = zipFile.entries();
            //遍历所有entry
            while (entryElement.hasMoreElements()) {
                entry = entryElement.nextElement();
                //获得entry的名字
                entryName = entry.getName();
                targetFileName = targetBaseDirName + entryName;
                if (entry.isDirectory()) {
                    //  如果entry是一个目录，则创建目录
                    new File(targetFileName).mkdirs();
                }
                else {
                    // 如果entry是一个文件，则创建父目录
                    new File(targetFileName).getParentFile().mkdirs();
                }
                //否则创建文件
                File targetFile = new File(targetFileName);
                System.out.println("创建文件：" + targetFile.getAbsolutePath());
                //打开文件输出流
                FileOutputStream os = new FileOutputStream(targetFile);
                //从ZipFile对象中打开entry的输入流
                InputStream is = zipFile.getInputStream(entry);
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                //关闭流
                os.close();
                is.close();
            }
        }
        catch (IOException err) {
            throw new RuntimeException("解压缩文件失败", err);
        }
    }

    /**
     * 将目录压缩到ZIP输出流。
     */
    private static void dirToZip(String baseDirPath, File dir,
                                 ZipOutputStream out) {
        if (dir.isDirectory()) {
            //列出dir目录下所有文件
            File[] files = dir.listFiles();
            // 如果是空文件夹
            if (files.length == 0) {
                ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));
                // 存储目录信息
                try {
                    out.putNextEntry(entry);
                    out.closeEntry();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    //如果是文件，调用fileToZip方法
                    CompressUtil.fileToZip(baseDirPath, files[i], out);
                }
                else {
                    //如果是目录，递归调用
                    CompressUtil.dirToZip(baseDirPath, files[i], out);
                }
            }
        }
    }

    /**
     * 将文件压缩到ZIP输出流
     */
    private static void fileToZip(String baseDirPath, File file,
                                  ZipOutputStream out) {
        ZipEntry entry;
        // 创建复制缓冲区
        byte[] buffer = new byte[4096];
        int bytesRead;
        if (file.isFile()) {
            try (FileInputStream in = new FileInputStream(file)) {
                // 创建一个文件输入流
                // 做一个ZipEntry
                entry = new ZipEntry(getEntryName(baseDirPath, file));
                // 存储项信息到压缩文件
                out.putNextEntry(entry);
                // 复制字节到压缩文件
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.closeEntry();
                in.close();
                System.out.println("添加文件"
                        + file.getAbsolutePath() + "被到ZIP文件中！");
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取待压缩文件在ZIP文件中entry的名字。即相对于跟目录的相对路径名
     *
     * @param baseDirPath
     * @param file
     * @return
     */
    private static String getEntryName(String baseDirPath, File file) {
        if (!baseDirPath.endsWith(File.separator)) {
            baseDirPath += File.separator;
        }
        String filePath = file.getAbsolutePath();
        // 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存储。
        if (file.isDirectory()) {
            filePath += "/";
        }
        int index = filePath.indexOf(baseDirPath);
        return filePath.substring(index + baseDirPath.length());
    }

    /**
     * 使用gzip压缩字符串
     *
     * @param str 要压缩的字符串
     * @return java.lang.String

     * Created DateTime 2023-12-09 20:45
     */
    public static String compress(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    /**
     * 使用gzip解压缩
     *
     * @param compressedStr 压缩的字符串
     * @return java.lang.String
     * Created DateTime 2023-12-09 20:47
     */
    public static String unCompress(String compressedStr) {
        if (compressedStr == null) {
            throw new IllegalArgumentException("压缩的字符串未设定。");
        }
        try (
//                ByteArrayInputStream in = new ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(compressedStr));
                ByteArrayInputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(compressedStr));
                GZIPInputStream gInZip = new GZIPInputStream(in);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = gInZip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
