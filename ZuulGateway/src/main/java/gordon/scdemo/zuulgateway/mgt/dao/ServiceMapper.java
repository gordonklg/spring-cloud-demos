package gordon.scdemo.zuulgateway.mgt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import gordon.scdemo.zuulgateway.mgt.entity.ApiService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 接口服务。
 * </p>
 *
 * @author Mybatis Plus Generator
 * @since 2019-08-27
 */
@Mapper
@Repository
public interface ServiceMapper extends BaseMapper<ApiService> {

}
