package ${basePackage}.service.impl;

import ${basePackage}.mapper.${domainNameUpperCamel}Mapper;
import ${basePackage}.domain.${domainNameUpperCamel};
import ${basePackage}.service.${domainNameUpperCamel}Service;
import ${basePackage}.dto.${domainNameUpperCamel}VO;
import com.online4edu.dependencies.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.${pkDataType};

import javax.annotation.Resource;

/**
 * ${description} - ServiceImpl 接口实现类
 * @author ${author}
 * @date ${date}
 */
@Service
// @Transactional
public class ${domainNameUpperCamel}ServiceImpl extends BaseServiceImpl<${domainNameUpperCamel}, ${domainNameUpperCamel}VO, ${domainNameUpperCamel}Mapper> implements ${domainNameUpperCamel}Service {

}
