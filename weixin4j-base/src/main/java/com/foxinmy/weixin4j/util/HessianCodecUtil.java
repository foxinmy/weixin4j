package com.foxinmy.weixin4j.util;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by songlin.luo on 2016/11/3.
 */
public final class HessianCodecUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HessianCodecUtil.class);

    private HessianCodecUtil() {

    }

    /**
     * 对象变成byte数组
     *
     * @param obj
     * @return
     */
    public static byte[] encode(Object obj) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(byteArray);
        try {
            output.writeObject(obj);
            output.flush();
            output.close();
            return byteArray.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                LOGGER.error("Close output error.", e);
            }
        }
    }

    /**
     * 将byte数组转回对象
     *
     * @param data
     * @return
     */
    public static Object decode(byte[] data) {
        final Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        try {
            return input.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                LOGGER.error("Close output error.", e);
            }
        }
    }
}
