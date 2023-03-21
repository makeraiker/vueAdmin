import Vue from "vue"

Vue.mixin({
	methods: {
		//判断用户有无权限方法   全局都能引用
		hasAuth(perm) {
			//menus.js下查看元素     permsList：权限列表
			var authority = this.$store.state.menus.permsList
			/*console.log(authority)
			console.log(authority.indexOf(perm) > -1)*/
			//判断该权限是否在权限列表中		>0在第一个位置依次往后，-1表示没有该权限
			return authority.indexOf(perm) > -1
		}
	}
})