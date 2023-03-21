package com.me.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.me.reggie.common.CustomException;
import com.me.reggie.entity.Category;
import com.me.reggie.entity.Dish;
import com.me.reggie.entity.Setmeal;
import com.me.reggie.mapper.CategoryMapper;
import com.me.reggie.service.CategoryService;
import com.me.reggie.service.DishService;
import com.me.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category>
        implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除前判断是否有关联分类
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);

        //查询当前分类是否关联菜品，关联则抛出义务异常
        if (count > 0){
            //有关联菜品，抛出异常
            throw new CustomException("当前分类下有关联菜品，不能删除");
        }

        //查询当前分类是否关联了套餐，如果关联抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);

        if (count1 > 0){
            //有关联套餐，抛异常
            throw new CustomException("当前分类下有关联套餐，不能删除");
        }
        //正常删除
        super.removeById(id);
    }
}
