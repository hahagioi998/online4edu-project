package com.online4edu.app.gateway.service.impl;

import com.online4edu.app.gateway.domain.SysAdministrativeRegion;
import com.online4edu.app.gateway.mapper.SysAdministrativeRegionMapper;
import com.online4edu.app.gateway.service.SysAdministrativeRegionService;
import com.online4edu.app.gateway.vo.SysAdministrativeRegionVO;
import com.online4edu.dependencies.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 行政区划 - ServiceImpl 接口实现类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 13/03/2021 21:38
 */
@Service
// @Transactional
public class SysAdministrativeRegionServiceImpl
        extends BaseServiceImpl<SysAdministrativeRegion, SysAdministrativeRegionVO, SysAdministrativeRegionMapper>
        implements SysAdministrativeRegionService {

}
