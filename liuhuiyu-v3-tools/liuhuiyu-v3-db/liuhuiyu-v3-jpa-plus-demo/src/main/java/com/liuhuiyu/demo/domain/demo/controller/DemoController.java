package com.liuhuiyu.demo.domain.demo.controller;

import com.liuhuiyu.demo.constant.WebAddressConstant;
import com.liuhuiyu.web.controller.AbsBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能<p>
 * Created on 2025/7/29 19:34
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@RestController
@RequestMapping(DemoController.ROOT)
public class DemoController extends AbsBaseController {

    static final String ROOT = WebAddressConstant.DEMO_TEST_ADDRESS_ROOT;


}
