package com.liuhuiyu.util.file;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-12-13 17:19
 */
public class FileUtilTest {

    @Test
    public void test01() {
        String s = "\\a\\c\\d.e.f.g";
        File file = new File(s);
        String fullFileName=FileUtil.getFullFileName(s);
        final String ext = FileUtil.getExt(s);
        final String fileName = FileUtil.getFileName(s);
        final String yyyyMMdd = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}