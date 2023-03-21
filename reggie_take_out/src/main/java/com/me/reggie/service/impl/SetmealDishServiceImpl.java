package com.me.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.me.reggie.entity.SetmealDish;
import com.me.reggie.mapper.SetmealDishMapper;
import com.me.reggie.service.SetmealDishService;
import com.me.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
