import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default ({
    state: {
        //导航列表
        menuList:[],
        //权限列表
        permsList:[],

        //判断是否为第一次请求路由
        hasRoute:false,

        editableTabsValue: 'Index',
        editableTabs: [{
            title: '首页',
            name: 'Index',

        }],

    },
    getters: {
    },
    mutations: {
        //动态获取权限
        setPermList(state,perms){
            state.permsList = perms
        },

        //获取权限对应导航功能
        setMenuList(state,menus){
            state.menuList = menus
        },

        changeRouteStatus(state,hasRoute){
            state.hasRoute=hasRoute

        },
        //添加便签页方法
        addTab(state,tab) {

            let index = state.editableTabs.findIndex(e => e.name === tab.name)

            if(index === -1){
                let newTabName = '';
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name,

                });
            }

            state.editableTabsValue = tab.name;
        },
        //退出方法操作：清空token、menuList（便签页）设置为空
        //反正就是把这些数据都清空
        resetState: (state => {
            state.token='';
            state.menuList=[];
            state.permsList=[];
            state.hasRoute=false;
            state.editableTabsValue='Index';
            state.editableTabs=[{
                title: '首页',
                name: 'Index',

            }];
        })
    },
    actions: {
    },

})
