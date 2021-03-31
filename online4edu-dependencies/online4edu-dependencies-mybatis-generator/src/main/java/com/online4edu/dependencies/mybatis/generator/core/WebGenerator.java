package com.online4edu.dependencies.mybatis.generator.core;

import com.online4edu.dependencies.mybatis.generator.domain.ProjectProperties;
import com.online4edu.dependencies.mybatis.generator.domain.TableSign;
import com.online4edu.dependencies.mybatis.generator.util.AutoUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.List;

/**
 * Controller 文件生成
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/30 21:41
 */
public class WebGenerator implements Generator {

    @Override
    public void fileGenerator(TableSign tableSign) {
        fileGenerator(Collections.singletonList(tableSign));
    }

    @Override
    public void fileGenerator(List<TableSign> tableSignList) {
        try {
            for (TableSign tableSign : tableSignList) {
                ProjectProperties instance = ProjectProperties.getInstance();
                String servicePath = instance.getJavaPath() + AutoUtil.packageConvertPath(instance.getPackageWeb()) + tableSign.getDomainName();
                File file = new File(servicePath + "Controller.java");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                AutoUtil.ftlConfiguration().getTemplate("web.ftl").process(tableSign, new FileWriter(file));
                System.out.println(tableSign.getDomainName() + "Controller.java 生成成功");

            }
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }
    }
}