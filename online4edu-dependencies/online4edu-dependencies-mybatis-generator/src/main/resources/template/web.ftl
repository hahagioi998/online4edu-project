package ${basePackage}.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${basePackage}.domain.${domainNameUpperCamel};
import ${basePackage}.service.${domainNameUpperCamel}Service;
import com.online4edu.dependencies.utils.result.ResponseMsgUtil;
import com.online4edu.dependencies.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "${description} - API")
@RestController
@RequestMapping("/${domainNameLowerCamel}")
public class ${domainNameUpperCamel}Controller {

    @Autowired
    private ${domainNameUpperCamel}Service ${domainNameLowerCamel}Service;

    @GetMapping("/{id}")
    @ApiOperation("通过主键ID查询")
    @ApiImplicitParam(name = "id", value = "主键ID")
    public Result<${domainNameUpperCamel}> get(@PathVariable ${pkJavaType} id) {
        return ResponseMsgUtil.success(${domainNameLowerCamel}Service.getById(id));
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNumber", value = "页码", defaultValue = "1", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "页数", defaultValue = "10", paramType = "query")
    })
    public Result<${"IPage<"}${domainNameUpperCamel}${">"}> page(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        IPage<${domainNameUpperCamel}> page = new Page<>(pageNumber, pageSize);
        return ResponseMsgUtil.success(${domainNameLowerCamel}Service.page(page));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("通过主键ID删除")
    @ApiImplicitParam(name = "id", value = "主键ID")
    public Result<${"Boolean"}> delete(@PathVariable ${pkJavaType} id) {
        return ResponseMsgUtil.success(${domainNameLowerCamel}Service.deleteById(id));
    }

    @DeleteMapping("/deleteBatch")
    @ApiOperation("通过主键ID集合批量删除")
    @ApiImplicitParam(name = "idList", value = "主键ID集合")
    public Result<${"Integer"}> deleteBatch(@RequestBody List<${pkJavaType}> idList) {
        return ResponseMsgUtil.success(${domainNameLowerCamel}Service.deleteBatch(idList));
    }
}