package com.liuhuiyu.util.date;


import java.time.LocalDateTime;


/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-07 14:10
 */
public class LocalDateUtilTest  {
    public void testStringToDate(){
        String dateStr="2021-02-11";
        LocalDateTime time2=LocalDateUtil.stringToDateTime(dateStr);
//        log.info(time2);
//        LocalDateTime time1 = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-d H:m:s"));
//        log.info(time1);
    }

}