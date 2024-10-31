package cc.liuhuiyu.db.demo.controller;

import com.liuhuiyu.db.demo.constant.WebAddressConstant;
import com.liuhuiyu.web.controller.AbsBaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-11-13 14:15
 */
@Controller
@RequestMapping(HomeController.ROOT)
@Api(value = "首页工具", tags = "首页工具")
public class HomeController extends AbsBaseController {
    static final String ROOT = WebAddressConstant.HOME_ADDRESS_ROOT;

    //region 首页地址访问
    @ApiIgnore
    @RequestMapping(value = {PATH_INDEX, PATH_DEFAULT})
    public String index() {
        return "redirect:" + WebAddressConstant.SWAGGER_UI_URL;
    }
    //endregion
}
