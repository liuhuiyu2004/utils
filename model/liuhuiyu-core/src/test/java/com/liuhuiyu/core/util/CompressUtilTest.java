package com.liuhuiyu.core.util;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-12-08 16:37
 */
class CompressUtilTest extends AbstractTest {
    @DisplayName("压缩解压")
    @ParameterizedTest
    @MethodSource("getStringStream")
    public void compress(String str){
        final String compress = CompressUtil.compress(str);
        final String uncompress = CompressUtil.uncompress(compress);
        LOG.info("原始：{}",str.length(),str);
        LOG.info("压缩后：{}",compress.length(),compress);
        LOG.info("还原后{}",uncompress.length(),uncompress);

    }

    static Stream<Arguments> getStringStream(){
        return Stream.of(
                Arguments.of("{\"caption\":\"\",\"stateIds\":[1300001,1300002,1300003,1300007],\"staffName\":\"\",\"creatorId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"paging\":{\"pageIndex\":0,\"pageSize\":15,\"allInOne\":false,\"sort\":[]},\"operating\":{\"operatorId\":\"8a9481e47e9e7cf3017e9e7d44f50187\"}}"),
                Arguments.of("{\"flag\":0,\"msg\":\"操作成功\",\"data\":{\"total\":17,\"content\":[{\"id\":\"8a9481e4817fd212018183fea2d8017a\",\"orderNumber\":\"P202206210010\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"送桶装氨水进厂。\",\"beginTime\":\"2022-06-22 08:00:00\",\"endTime\":\"2022-06-22 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202206210010\",\"abstractInfo\":\"人员信息：(湛江市大华化工有限公司)陈年;周健生;车辆信息：桂MZL359;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e4813339fc0181423b590a0507\",\"orderNumber\":\"P202206080013\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"\",\"beginTime\":\"2022-06-09 08:00:00\",\"endTime\":\"2022-06-09 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202206080013\",\"abstractInfo\":\"人员信息：(台山市中建物资有限公司)李燕华;李德龙;车辆信息：桂E06378;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e4813339fc018137ad9ae901c5\",\"orderNumber\":\"P202206060017\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"送次氯酸钠进厂。\",\"beginTime\":\"2022-06-07 08:00:00\",\"endTime\":\"2022-06-07 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202206060017\",\"abstractInfo\":\"人员信息：(湛江市大华化工有限公司)叶国显;(湛江甲川化工)袁志颖;车辆信息：桂R81568;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e4804a1ae401805e043347060a\",\"orderNumber\":\"P202204250001\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"送桶装氨水进厂。\",\"beginTime\":\"2022-04-27 08:00:00\",\"endTime\":\"2022-04-27 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202204250001\",\"abstractInfo\":\"人员信息：(湛江市大华化工有限公司)黄健华;周健生;车辆信息：桂MZL359;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47ffcc8fc018044d20f2613d5\",\"orderNumber\":\"P202204200012\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"送氢氧化钠进厂\",\"beginTime\":\"2022-04-20 08:00:00\",\"endTime\":\"2022-04-20 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202204200012\",\"abstractInfo\":\"人员信息：(台山市中建物资有限公司)黄小丽;车辆信息：桂E09579;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47ffcc8fc01803a7ad9ab0ebb\",\"orderNumber\":\"P202204180010\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"送氢氧化钠进厂。\",\"beginTime\":\"2022-04-20 08:00:00\",\"endTime\":\"2022-04-20 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202204180010\",\"abstractInfo\":\"人员信息：(台山市中建物资有限公司)李剑;车辆信息：桂E09579;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47ffcc8fc018021b57fc1092b\",\"orderNumber\":\"P202204130016\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"送桶装氨水进厂。\",\"beginTime\":\"2022-04-15 08:00:00\",\"endTime\":\"2022-04-15 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202204130016\",\"abstractInfo\":\"人员信息：(湛江市大华化工有限公司)余江华;周宏光;车辆信息：桂MB5235;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47ffcc8fc018020b40404080e\",\"orderNumber\":\"P202204130013\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"送氢氧化钠进厂。\",\"beginTime\":\"2022-04-14 08:00:00\",\"endTime\":\"2022-04-14 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202204130013\",\"abstractInfo\":\"人员信息：(大华化工)袁志颖;(湛江市大华化工有限公司)叶国显;车辆信息：赣D92802;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47ffcc8fc0180164b49dd0378\",\"orderNumber\":\"P202204110014\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"送盐酸进厂。\",\"beginTime\":\"2022-04-13 08:00:00\",\"endTime\":\"2022-04-13 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202204110014\",\"abstractInfo\":\"人员信息：(浩瀚化工有限公司)周柏友;彭金联;车辆信息：粤S61193;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47ffcc8fc018017829ff90502\",\"orderNumber\":\"P202204110031\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"进厂核对仪表配件型号。\",\"beginTime\":\"2022-04-12 08:00:00\",\"endTime\":\"2022-04-12 17:00:59\",\"site\":\"办公区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202204110031\",\"abstractInfo\":\"人员信息：(湛江市广慕商贸有限公司)陈玉梅;车辆信息：粤GXW291;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47fcb2352017fd2f44ff6021c\",\"orderNumber\":\"P202203290002\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"进厂回收空桶\",\"beginTime\":\"2022-03-30 08:00:00\",\"endTime\":\"2022-03-30 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202203290002\",\"abstractInfo\":\"人员信息：(湛江大华化工有限公司)朱锦林;车辆信息：粤GD010Q;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47fa00f76017faa9543320135\",\"orderNumber\":\"P202203210017\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"供货商送次氯酸钠进厂\",\"beginTime\":\"2022-03-23 08:00:00\",\"endTime\":\"2022-03-23 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202203210017\",\"abstractInfo\":\"人员信息：(大华化工)袁志颖;(湛江市大华化工有限公司)叶国显;车辆信息：赣D92802;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47fa00f76017fa9caf44b000e\",\"orderNumber\":\"P202203210002\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"供货商送氢氧化钠进厂\",\"beginTime\":\"2022-03-22 08:00:00\",\"endTime\":\"2022-03-22 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202203210002\",\"abstractInfo\":\"人员信息：(台山市中建物资有限公司)李土兴;李德龙;车辆信息：桂E06378;桂E2770挂;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47fa00f76017faa2d10f600d6\",\"orderNumber\":\"P202203210012\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"新入厂员工安全培训\",\"beginTime\":\"2022-03-21 08:00:00\",\"endTime\":\"2022-03-25 17:00:59\",\"site\":\"全厂区域\",\"approvalRemark\":\"\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202203210012\",\"abstractInfo\":\"人员信息：(湖南火电)陈鸿跃;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]},{\"id\":\"8a9481e47f76f367017f861170840155\",\"orderNumber\":\"P202203140011\",\"branchId\":\"8a9481e47e9e7cf3017e9e7d44e60185\",\"staffId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"reason\":\"供货商送氨水进厂\",\"beginTime\":\"2022-03-16 08:00:00\",\"endTime\":\"2022-03-16 17:00:59\",\"site\":\"生产区域\",\"approvalRemark\":\"供货商送氨水进厂\",\"gatekeepersRemark\":\"\",\"branch\":\"化学白班\",\"staffName\":\"陈伟智\",\"updaterId\":\"8a9481e47e9e7cf3017e9e7d44f50187\",\"stateId\":1300003,\"state\":\"已审核\",\"caption\":\"P202203140011\",\"abstractInfo\":\"人员信息：(湛江市大华化工有限公司)林海理;周宏光;车辆信息：桂MB5235;\",\"visitsCarList\":[],\"visitsMemberList\":[],\"submitType\":\"\",\"taskList\":[],\"completedTaskList\":[],\"inProgressTaskList\":[]}],\"pageable\":{\"sort\":{\"orders\":[]},\"page\":0,\"size\":15}}}")
        );
    }
}