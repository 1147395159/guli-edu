package com.yl.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.commontils.R;
import com.yl.eduservice.entity.EduTeacher;
import com.yl.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-09
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    //注入service
    @Autowired
    private EduTeacherService teacherService;

    //1,查询讲师表中的所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //2,讲师逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")//通过路径传id值
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        boolean flag = teacherService.removeById(id);

        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //3,分页查询讲师的方法
    //current 当前页
    //limit 每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //调用方法实现分页
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录条数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        Map map = new HashMap();
        map.put("total",total);
        map .put("rows",records);

        return R.ok().data(map);
    }
}

