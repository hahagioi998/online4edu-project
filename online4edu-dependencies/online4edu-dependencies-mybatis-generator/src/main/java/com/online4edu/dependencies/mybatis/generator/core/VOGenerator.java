package com.online4edu.dependencies.mybatis.generator.core;

import com.online4edu.dependencies.mybatis.generator.domain.ProjectProperties;
import com.online4edu.dependencies.mybatis.generator.domain.TableSign;
import com.online4edu.dependencies.mybatis.generator.util.AutoUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * VO 扩展文件生成
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/30 21:41
 */
public class VOGenerator implements Generator {

    @Override
    public void fileGenerator(List<TableSign> tableSignList) {
        try {
            for (TableSign tableSign : tableSignList) {
                ProjectProperties instance = ProjectProperties.getInstance();
                String servicePath = instance.getJavaPath() + AutoUtil.packageConvertPath(instance.getPackageVO()) + tableSign.getDomainName();
                File file = new File(servicePath + "VO.java");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                AutoUtil.ftlConfiguration().getTemplate("vo.ftl").process(tableSign, new FileWriter(file));
                System.out.println(tableSign.getDomainName() + "VO.java 生成成功");
            }
        } catch (Exception var12) {
            throw new RuntimeException("生成VO失败", var12);
        }
    }
}
