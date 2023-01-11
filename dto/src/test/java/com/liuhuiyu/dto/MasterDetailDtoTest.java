package com.liuhuiyu.dto;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-12-01 9:51
 */
class MasterDetailDtoTest extends TestBase {
    @Test
    public void create() {
        MasterDetailDto<String, String> dto = new MasterDetailDto<>();
        dto.setMaster("A01");
        dto.setDetail(Arrays.stream(new String[]{"B01", "B02", "B03"}).collect(Collectors.toList()));
        LOG.info("{}:{}", dto.getMaster(), dto.getDetail());
    }
}