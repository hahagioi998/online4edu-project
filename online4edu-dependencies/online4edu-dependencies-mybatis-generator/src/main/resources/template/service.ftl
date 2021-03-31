package ${basePackage}.service;

import ${basePackage}.domain.${domainNameUpperCamel};
import ${basePackage}.vo.${domainNameUpperCamel}VO;
import com.online4edu.dependencies.mybatis.service.BaseService;
import java.lang.${pkJavaType};

/**
 * ${description} - Service接口类
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${domainNameUpperCamel}Service
        extends BaseService<${domainNameUpperCamel}, ${domainNameUpperCamel}VO, ${pkJavaType}> {

}
