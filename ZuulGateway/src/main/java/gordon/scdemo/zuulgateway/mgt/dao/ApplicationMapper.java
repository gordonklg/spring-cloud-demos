package gordon.scdemo.zuulgateway.mgt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import gordon.scdemo.zuulgateway.mgt.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 接入方应用程序，网关外部系统 Mapper 接口
 * </p>
 *
 * @author Mybatis Plus Generator
 * @since 2019-08-27
 */
@Mapper
@Repository
public interface ApplicationMapper extends BaseMapper<Application> {

}
