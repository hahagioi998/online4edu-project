package com.online4edu.dependencies.mybatis.generator.core;

import com.online4edu.dependencies.mybatis.generator.domain.ProjectProperties;
import com.online4edu.dependencies.mybatis.generator.domain.TableSign;
import com.online4edu.dependencies.mybatis.generator.util.AutoUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Service 接口文件生成
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/30 21:41
 */
public class ServiceGenerator implements Generator {

    @Override
    public void fileGenerator(List<TableSign> tableSignList) {
        try {
            for (TableSign tableSign : tableSignList) {
                // 生成 Service.java
                ProjectProperties instance = ProjectProperties.getInstance();
                String servicePath = instance.getJavaPath() + AutoUtil.packageConvertPath(instance.getPackageService()) + tableSign.getDomainName();
                File serviceFile = new File(servicePath + "Service.java");
                if (!serviceFile.getParentFile().exists()) {
                    serviceFile.getParentFile().mkdirs();
                }
                AutoUtil.ftlConfiguration().getTemplate("service.ftl").process(tableSign, new FileWriter(serviceFile));
                System.out.println(tableSign.getDomainName() + "Service.java 生成成功");

                // 生成 ServiceImpl.java
                String serviceImplPath = instance.getJavaPath() + AutoUtil.packageConvertPath(instance.getPackageServiceImpl()) + tableSign.getDomainName();
                File serviceImplFile = new File(serviceImplPath + "ServiceImpl.java");
                if (!serviceImplFile.getParentFile().exists()) {
                    serviceImplFile.getParentFile().mkdirs();
                }

                AutoUtil.ftlConfiguration().getTemplate("service-impl.ftl").process(tableSign, new FileWriter(serviceImplFile));
                System.out.println(tableSign.getDomainName() + "ServiceImpl.java 生成成功");
            }
        } catch (Exception var14) {
            throw new RuntimeException("生成Service失败", var14);
        }
    }

}
