package com.me.reggie.dto;

import com.me.reggie.entity.Setmeal;
import com.me.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
