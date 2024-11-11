package cc.liuhuiyu.db.demo.domain.demo.dao;

import cc.liuhuiyu.db.demo.db.AbstractDbView;
import cc.liuhuiyu.db.demo.db.AbstractSqlCommandPackageDb;
import com.liuhuiyu.core.help.sql.SelectSql;
import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.db.demo.domain.demo.in.DemoTestFindDto;
import com.liuhuiyu.db.demo.domain.demo.out.DemoTestDto;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/12/16 9:26
 */
@Repository
public class DemoTestDao extends AbstractDbView<DemoTestDto, DemoTestFindDto> {
    public DemoTestDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected void fullWhere(DemoTestFindDto whereDto, SelectSql selectSql) {
        new DemoTestFindCommandPackage(whereDto, selectSql).runCommandPackage();
    }

    @Override
    protected String getBaseSql() {
        return DemoTestDto.getBaseSql();
    }

    @Override
    protected String getCountSql() {
        return DemoTestDto.getCountSql();
    }

    @Override
    protected String getOrderSql() {
        return "ORDER BY ID DESC";
    }


    /**
     * 查询信息填充封包
     *
     * @author LiuHuiYu
     * Created DateTime 2024-01-13 17:35
     */
    static class DemoTestFindCommandPackage extends AbstractSqlCommandPackageDb<DemoTestFindDto> {
        static Map<String, Consumer<DemoTestFindCommandPackage>> runnableMap = new HashMap<>();

        static {
            runnableMap.put(DemoTestFindDto.FIND_BY_ID_MODEL, DemoTestFindCommandPackage::findById);
        }

        /**
         * SQL原生封装 构建函数
         *
         * @param findDto   查询条件
         * @param selectSql sql
         * @author LiuHuiYu
         * Created DateTime 2022-11-20 8:27
         */
        public DemoTestFindCommandPackage(DemoTestFindDto findDto, SelectSql selectSql) {
            super(findDto, selectSql);
        }

        @Override
        protected void commandPackage() {
            runnableMap.getOrDefault(this.findDto.getOperating().getModelName(), DemoTestFindCommandPackage::define).accept(this);
        }

        private void define() {
            throw new RuntimeException("未设定");
        }

        private void findById() {
            Assert.assertTrue(StringUtils.hasText(this.findDto.getId()), "未设定ID");
            this.conditionAnd("T.ID").eq(this.findDto.getId());
        }
    }
}