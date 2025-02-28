package com.liuhuiyu.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/19 9:13
 */
public class DeepCopyUtil {
    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T original) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(original);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        }
        catch (Exception e) {
//             throws IOException, ClassNotFoundException
            throw new RuntimeException("深度clone数据错误", e);
        }
    }
}
