<template>
    <el-container>

        <SideMenu></SideMenu>

        <el-container>
            <el-header>
                <strong>后台管理系统</strong>
                <div class="header-avatar">
                    <el-avatar size="medium" :src="imgPath"></el-avatar>
                    <el-dropdown>
                        <span class="el-dropdown-link">
                            {{userInfo.username}}<i class="el-icon-arrow-down el-icon--right"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>
                                <router-link to="/userCenter">个人中心</router-link>
                            </el-dropdown-item>
                            <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>

                    <el-link href="https://markerhub.com" target="_blank">网站</el-link>
                    <el-link href="" target="_blank">B站</el-link>
                </div>
            </el-header>
            <el-main>
                <Tabs></Tabs>

                <div style="margin: 0 15px">
                    <router-view></router-view>
                </div>

            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import SideMenu from "@/views/inc/SideMenu";
    import Tabs from "@/views/inc/Tabs";

    export default {
        name: "Home",
        components: {
            SideMenu, Tabs
        },
        data() {
            return {
                imgPath: '',
                userInfo: {
                    id: "",
                    username: "",
                    avatar: ""
                }
            }
        },
        created() {
            this.getUserInfo()
        },
        methods: {
            getUserInfo() {
                this.$axios.get("/sys/userInfo").then(res => {
                    this.userInfo = res.data.data
                    this.imgPath = "http://localhost:8081" + this.userInfo.avatar;

                })
            },
            logout() {
                this.$axios.post("/logout").then(res => {
                    localStorage.clear()
                    sessionStorage.clear()

                    this.$store.commit("resetState")

                    this.$router.push("/login")
                })
            }
        }
    }
</script>

<style scoped>
    .header-avatar {
        float: right;
        width: 210px;
        display: flex;
        justify-content: space-around;
        align-items: center;
    }

    .el-container {
        padding: 0px;
        margin: 0px;
        height: 100vh;
    }

    .el-header {
        background-color: #17B3A3;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;

        line-height: 200px;
    }

    .el-main {
        color: #333;
        /*text-align: center;*/
        /*line-height: 160px;*/
        padding: 0;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }

    .el-dropdown-link {
        cursor: pointer;
        /*color: #409EFF;*/
    }

    .el-icon-arrow-down {
        font-size: 12px;
    }

    .el-menu-vertical-demo {
        height: 100%;
    }

    a {
        text-decoration: none;
    }
</style>