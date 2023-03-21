<template>
    <el-row type="flex" class="row-bg" justify="center" style="height: 100%">
        <el-col :xl="6" :lg="7"><div class="grid-content bg-purple">
            <h2>后台管理登录</h2>
            <el-image :src="require('@/assets/logo.png')"></el-image>
            <p>官方logo</p>
            <p>请登录</p>
        </div></el-col>
        <el-col :xl="6" :lg="7"><div class="grid-content bg-purple-light">
            <el-divider direction="vertical"></el-divider>
        </div></el-col>
        <el-col :xl="6" :lg="7">
            <div class="grid-content bg-purple">
                <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="demo-loginForm">
                    <el-form-item label="用户名" prop="username" style="width: 380px">
                        <el-input v-model="loginForm.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password" style="width: 380px">
                        <el-input v-model="loginForm.password" type="password"></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" prop="code" style="width: 380px">
                        <el-input v-model="loginForm.code" style="width: 172px;float: left"></el-input>
                        <el-image :src="captchaImg" class="captchaImg" @click="getCaptcha"></el-image>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
                        <el-button @click="resetForm('loginForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-col>
    </el-row>
</template>

<script>

    import qs from "qs"

    export default {
        name: "Login",
        data() {
            return {
                loginForm: {
                    username: 'admin',
                    password: '111111',
                    code: '11111',
                    token: ''
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ],
                    code: [
                        { required: true, message: '请输入验证码', trigger: 'blur' },
                        { min: 5, max: 5, message: '长度在 5 个字符', trigger: 'blur' }
                    ]
                },
                captchaImg: null
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$axios.post('/login?'+qs.stringify(this.loginForm)).then(res =>{
                            const jwt = res.headers['authorization']

                            this.$store.commit('SET_TOKEN',jwt)

                            this.$router.push("/index")
                        })

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            getCaptcha(){
                this.$axios.get('/captcha').then(res => {

                    this.loginForm.token = res.data.data.token
                    this.captchaImg = res.data.data.captchaImg

                    this.loginForm.code=''
                })
            }
        },
        created() {
            this.getCaptcha()
        }
    }
</script>

<style scoped>

    .template{
        background-color: #fafafa;
        align-items: center;
    }
    .el-row{
        background-color: #fafafa;
        height: 100vh;
        display: flex;
        align-items: center;
        text-align: center;
        justify-content: center;
    }

    .el-divider{
        height: 200px;
    }

    .captchaImg{
        float: left;
        margin-left: 10px;
        border-radius: 4px;
    }

</style>