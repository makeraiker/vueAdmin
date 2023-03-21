package com.me.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.me.reggie.entity.ShoppingCart;
import com.me.reggie.mapper.ShoppingCatMapper;
import com.me.reggie.service.ShoppingCatService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCatServiceImpl extends ServiceImpl<ShoppingCatMapper, ShoppingCart> implements ShoppingCatService {
}
