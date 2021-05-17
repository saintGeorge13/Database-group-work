<template>
    <div class="login-info">
        <el-tabs type="border-card" :stretch="true" class="border-card" >
        <el-tab-pane label="登录">
        <el-form
           autocomplete="on"
           :model="loginForm"
           ref="loginForm"
           label-position="left"
        >
            <el-form-item label="用户名">
            <el-input v-model="loginForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码">
            <el-input v-model="loginForm.password" show-password></el-input>
            </el-form-item>
            <el-form-item>
            <el-button @click.native.prevent="handleLogin">登录</el-button>
            <el-button @click.native.prevent="ManagerLogin">管理员登录</el-button>
            </el-form-item>
        </el-form>
        </el-tab-pane> 
        <el-tab-pane label="注册">
        <el-form>
            <el-form-item label="用户名">
                <el-input v-model="registerForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="registerForm.password" show-password></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="registerForm.mailbox"></el-input>
            </el-form-item>
            <el-form-item label="验证码">
               <el-input class="code" v-model="registerForm.code"></el-input>
               <el-button @click="registerCode">获取验证码</el-button>
            </el-form-item>
            
            <el-form-item label="姓名">
                <el-input v-model="registerForm.name"></el-input>
            </el-form-item>
            <el-form-item label="性别">
                <el-select v-model="registerForm.sex"  placeholder="请选择">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="学校">
                <el-input v-model="registerForm.college"></el-input>
            </el-form-item>
            <el-form-item label="专业">
                <el-input v-model="registerForm.major"></el-input>
            </el-form-item>
            <el-form-item label="宿舍">
                <el-input v-model="registerForm.dormitory"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="signup">注册</el-button>
            </el-form-item>
        </el-form>
        </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import util from '../util';
    export default {
        name: "login",
        data(){
            return {
                login: false,
                register: true,
                loginForm:{
                    username: "",
                    password: "",
                    
                },
                registerForm:{
                    username: "",
                    password: "",
                    mailbox: "",
                    code: "",
                    name: "",
                    sex: "",
                    college: "",
                    major: "",
                    dormitory: ""
                },
                confirmPassword: '',
                options: ['男', '女'],
            }
        },
        methods: {
            registerCode(){
                util.registerCode(this.registerForm.mailbox).then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    //console.log(response.data.data);
                } 
                else {
                    window.alert(response.data.msg);
                }
            }) 
            },
            signup(){
                console.log(this.registerForm);
                var objData = {};
                objData["studentID"] = this.registerForm.username;
                objData["password"] = this.registerForm.password;
                objData["mailbox"] = this.registerForm.mailbox;
                objData["code"] = this.registerForm.code;
                objData["name"] = this.registerForm.name;
                objData["sex"] = this.registerForm.sex;
                objData["college"] = this.registerForm.college;
                objData["major"] = this.registerForm.major;
                objData["dormitory"] = this.registerForm.dormitory;
                console.log(objData);
                util.register(objData).then(response => { //使用 login 接口进行网络请求
                            let code = response.data.code;
                            console.log(response);
                            if (code == 0) {
                                window.localStorage.setItem('username', this.registerForm.username);
                                window.localStorage.setItem('password', this.registerForm.password);
                                this.$store.commit('setUser', this.registerForm.username);  
                                this.$store.commit('setLoginStatus', true);
                                window.alert('登陆成功，确定进入网站首页');
                                window.location.href = '/list';
                            } 
                            else {
                                window.alert('注册失败');
                            }
                        })
            },
            ManagerLogin(){
                this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        util.admin_login(this.loginForm.username, this.loginForm.password).then(response => { //使用 login 接口进行网络请求
                            let code = response.data.code;
                            if (code == 0) {
                            window.localStorage.setItem('username', this.loginForm.username);
                            window.localStorage.setItem('password', this.loginForm.password);
                            window.localStorage.setItem('usertype', false);
                            this.$store.commit('setUser', this.loginForm.username);  
                            this.$store.commit('setLoginStatus', false);
                            this.$store.commit('setUserType', true);
                            window.alert('登陆成功，确定进入网站首页');
                            window.location.href = '/list';
                            } 
                            else {
                                window.alert('账号或密码错误');
                            }
                        })
                        
                    } 
                    else {
                        // eslint-disable-next-line no-console
                        console.log("参数验证不合法！");
                        return false;
                    }
                });
            },
            handleLogin() {
                    this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        util.login(this.loginForm.username, this.loginForm.password).then(response => { //使用 login 接口进行网络请求
                            let code = response.data.code;
                            if (code == 0) {
                            window.localStorage.setItem('username', this.loginForm.username);
                            window.localStorage.setItem('password', this.loginForm.password);
                            window.localStorage.setItem('usertype', true);
                            this.$store.commit('setUser', this.loginForm.username);  
                            this.$store.commit('setLoginStatus', true);
                            this.$store.commit('setUserType', true);
                            window.alert('登陆成功，确定进入网站首页');
                            window.location.href = '/list';
                            } 
                            else {
                                window.alert('账号或密码错误');
                            }
                        })
                        
                    } 
                    else {
                        // eslint-disable-next-line no-console
                        console.log("参数验证不合法！");
                        return false;
                    }
                });
            }         
        },
        created(){
            //获取路由中的参数
            if(this.$route.params.loginStatus === 'logout'){
                window.localStorage.clear();
                this.$store.commit('setLoginStatus', false);
                return;
            }
            const loginStatus = this.$store.state.loginStatus;
            if(loginStatus){
                this.login = false;
                this.register = false;
                window.alert('您已经是登录状态')
                window.location.href = '/list'
            }
        }
    }
</script>

<style scoped>
    .code{
        width: 50%;
        float: left;
    }
    .border-card {
        width: 60%;
        margin: 5% auto;
        height: 500px;
        overflow-y: scroll;
    }
    .login-info{
        height:100%;
        width:100%;
        text-align: center;
        font-size: 26px;
        position: absolute;
        bottom: 0;
        color: #fff;
        background: -webkit-linear-gradient( top, rgba(0, 0, 0, 0),rgba(0, 0, 0, 0.2) ); /* Safari 5.1 - 6 */
        background: -o-linear-gradient(bottom,rgba(0, 0, 0, 0),rgba(0, 0, 0, 0.2)); /* Opera 11.1 - 12*/
        background: -moz-linear-gradient( bottom, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.2)); /* Firefox 3.6 - 15*/
        background: linear-gradient(to bottom,rgba(16, 32, 43, 0), rgba(233, 33, 43, 0.5)); /* 标准的语法 */
    }
    .login-info-form{
        position: relative;
        top: 100px;
    }
    p{
         font-size: 26px;
         color: #5c6b77;
     }
    .submit{
        font-size: 18px;
    }
    span{
        font-size: 16px;
        color: #5c6b77;
    }
</style>