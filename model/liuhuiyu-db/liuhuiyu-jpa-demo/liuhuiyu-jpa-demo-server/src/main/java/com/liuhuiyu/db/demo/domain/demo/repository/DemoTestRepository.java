package com.liuhuiyu.db.demo.domain.demo.repository;

import com.liuhuiyu.db.demo.domain.demo.entity.DemoTest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/12/16 9:26
 */
public interface DemoTestRepository extends JpaRepository<DemoTest, String> {
}
