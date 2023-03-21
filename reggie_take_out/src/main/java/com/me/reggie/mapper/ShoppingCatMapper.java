package com.me.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.me.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCatMapper extends BaseMapper<ShoppingCart> {
}
