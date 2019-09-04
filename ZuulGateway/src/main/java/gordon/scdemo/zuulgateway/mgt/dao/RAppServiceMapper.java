package gordon.scdemo.zuulgateway.mgt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import gordon.scdemo.zuulgateway.mgt.entity.RAppService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * APP和SERVICE对应关系 Mapper 接口
 * </p>
 *
 * @author Mybatis Plus Generator
 * @since 2019-08-27
 */
@Mapper
@Repository
public interface RAppServiceMapper extends BaseMapper<RAppService> {

}
