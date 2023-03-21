import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Index from '../views/Index.vue'
import User from "@/views/sys/User";
import Menu from "@/views/sys/Menu";
import Role from "@/views/sys/Role";

import axios from "../axios"
import store from "../store";
import el from "element-ui/src/locale/lang/el";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    children:[
      {
        path: '/index',
        name: 'Index',
        meta:{
          title:"个人中心"
        },
        component: Index
      },
      {
        path: '/userCenter',
        name: 'userCenter',
        meta:{
          title:"个人中心"
        },
        component: () => import('../views/UserCenter.vue')//懒加载
      },
      /*{
        path: '/sys/users',
        name: 'SysUser',
        component: User
      },
      {
        path: '/sys/roles',
        name: 'SysRole',
        component: Role
      },
      {
        path: '/sys/menus',
        name: 'SysMenu',
        component: Menu
      }*/
    ]
  },
  {
    path: '/Index',
    name: 'Index',
    component: Index
  },
  {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/Login.vue')//懒加载
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{

  let hasRoute = store.state.menus.hasRoute

  let token = localStorage.getItem("token")

  if (to.path == '/login'){
    next()
  }else if(!token){
    next({path:'/login'})
  } else if(token && !hasRoute){ //判断是否为第一次请求路由
    axios.get("/sys/menu/nav",{
      headers:{
        Authorization:localStorage.getItem("token")
      }
    }).then(res=>{

      //拿到menuList
      store.commit("setMenuList",res.data.data.nav)


      //拿到用户权限
      store.commit("setPermList",res.data.data.authoritys)
      /*console.log(res.data.data.authoritys)*/

      //动态绑定路由
      let newRoutes = router.options.routes

      res.data.data.nav.forEach(menu => {
        if(menu.children){
          menu.children.forEach(e => {

            //转换路由
            let route = menuToRoute(e)
            //把路由添加到路由管理中
            if(route){
              newRoutes[0].children.push(route)
            }
          })
        }
      })
      router.addRoutes(newRoutes)
      hasRoute = true
      store.commit("changeRouteStatus",hasRoute)
    })
  }



  next();
})

/**
 * 导航转成路由
 * @param menu
 * @returns {null}
 */
const menuToRoute = (menu) => {
  //component为空则不转换路由--》指mock之下数据，父级菜单不需要路由
  if(!menu.component){
    return null
  }
  let route={
    name:menu.name,
    path:menu.path,
    meta:{
      icon:menu.icon,
      title:menu.title
    }
  }

  route.component =() =>import('../views/'+ menu.component +'.vue')//懒加载

  return route
}

export default router
