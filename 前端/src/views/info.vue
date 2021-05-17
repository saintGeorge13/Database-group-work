<template>
    <div class="login-info">
        <el-tabs type="border-card" :stretch="true" class="border-card" >
        <el-tab-pane  label="个人信息">
        <el-form>
            <el-form-item label="头像">
            <img class="product-image" 
                 :src= infoForm.image>
            </el-form-item>
  
            <el-upload
                ref="uploadForm"
                :before-upload="beforeUpload"
                :http-request="updateavatar"
                :limit="1"
                accept="image/png,image/jpg,image/jpeg"
                action="string"
                multiple
                >
                <el-button size="small" class="upload-btn" type="primary">
                    <span class="iconfont icon-shangchuan" />上传文件
                </el-button>
            </el-upload>
            <el-form-item label="姓名">
                <el-input v-model="infoForm.stuName"></el-input>
            </el-form-item>
            <el-form-item label="学生ID">
                <el-input v-model="infoForm.stuID" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="登录密码">
                <el-input v-model="infoForm.password"></el-input>
            </el-form-item>
            <el-form-item label="性别">
                <el-select v-model="infoForm.stuSex"  placeholder="请选择">
                    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="大学">
                <el-input v-model="infoForm.stuCol"></el-input>
            </el-form-item>
            <el-form-item label="专业">
                <el-input v-model="infoForm.stuMaj"></el-input>
            </el-form-item>
            <el-form-item label="宿舍">
                <el-input v-model="infoForm.stuDorm"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="updateuser">确认修改并上传</el-button>
            </el-form-item>
            <div class="cart-content-main">
            <div class="review-price">
                购买该卖家闲置
            </div>
            <div class="review-price">
                买家评论
            </div>
            <div class="review-price">
                买家打分
            </div>
            
            </div>
            <Review v-for="item in commentlist" :comment="item" :key="item.id"></Review> 
            <div class="review-not-found"
                v-show="!commentlist.length">暂无评论
            </div>
        </el-form>
        </el-tab-pane>
        <el-tab-pane label="我的订单">
            <div class="cart-content-main">
            <div class="info-img">
                宝贝图片
            </div>
            <div class="cart-price">
                宝贝名称
            </div>
            <div class="cart-price">
                宝贝价格
            </div>
            <div class="cart-price">
                卖家名称
            </div>
            
            <div class="cart-price">
                确认收货
            </div>
            <div class="cart-price">
                评价
            </div>
            </div>
            <div v-if="usertype === 'true'">
            <Order v-for="item in orderlist" :order="item" :key="item.ordID"></Order> 
                <div class="review-not-found"
                    v-show="!orderlist.length">暂无订单
                </div>
            </div>
            <div v-if="usertype === 'false'">
                <Order v-for="item in alllist" :order="item" :key="item.ordID"></Order> 
                <div class="review-not-found"
                    v-show="!orderlist.length">暂无订单
                </div>
            </div>
        </el-tab-pane>
        </el-tabs>
        <div class="clearfloat"></div>
    </div>
    
    
</template>

<script>
    import util from '../util';
    import Order from '../components/order.vue';
    import Review from '../components/review.vue';
    export default {
        components: {Order, Review},
        name: "sell",
        computed: {
        },
        data(){
            return {
                login: false,
                register: true,
                usertype: this.$store.state.usertype,
                infoForm:{
                    image: '',
                    stuID: '',
                    password: '',
                    stuName:'',
                    stuHead:'',
                    stuSex:'',
                    stuCol:'',
                    stuMaj: '',
                    stuDorm: '',  
                },
                orderlist: {},
                alllist: {},
                commentlist: {},
                options: [{
                  value: '1',
                  label: '男'
                }, 
                {
                  value: '2',
                  label: '女'
                }],

                list:{},
                cmtList:{},
              }
        },
        
        methods: {
            getValue(){
                console.log(this.infoForm);
            },
            beforeUpload(file) {
                const fileSuffix = file.name.substring(file.name.lastIndexOf(".") + 1);
                const extension1 = fileSuffix === "png";
                const extension2 = fileSuffix === "jpg";
                const extension3 = fileSuffix === "jpeg";
                const isLt2M = file.size / 1024 / 1024 < 0.5;
            
                if (!extension1 && !extension2 && !extension3) {
                    return false;
                }
            
                if (!isLt2M) {
                    return false;
                }
            },    
            updateavatar(item){
                const formData = new FormData();
                formData.append("avatar", item.file);
                console.log(item.file);  
                //this.infoForm.image = "https://106.55.166.56:8080/api/e-commerce/static/upload/avatar/123.jpg"
                util.avatar(formData).then(response => {       
                    let code = response.data.code;
                    if (code === 0) {
                        //this.$refs.imgForm.submit();
                        window.alert("头像修改成功");
                        
                        window.location.href = '/info';
                    } 
                    else {
                        window.alert(response.data.msg);
                    }
                })              
            },
            updatecomment(stuID)
            {
                util.comment(this.infoForm.stuID).then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    console.log(response.data.data);
                    this.commentlist = response.data.data;
                    //console.log(this.commentlist);
                } 
                else {
                    window.alert(response.data.msg);
                }
            })
            },
            updateuser(item)
            {
                var objData = {};
                objData["password"] = this.infoForm.password;
                objData["name"] = this.infoForm.stuName;
                objData["sex"] = this.infoForm.stuSex;
                objData["college"] = this.infoForm.stuCol;
                objData["major"] = this.infoForm.stuMaj;
                objData["dormitory"] = this.infoForm.stuDorm;
                console.log(objData);
                
                util.modifyuser(objData).then(response => {       
                    let code = response.data.code;
                    if (code === 0) {
                        window.alert("信息修改成功");
                        window.location.href = '/info';
                    } 
                    else {
                        window.alert(response.data.msg);
                    }
                })
                
            },
        
        },
        mounted(){
            //this.$store.dispatch('getorderList');    

            util.user().then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    console.log(response);
                    //updateInfo(response.data.data);
                    this.infoForm.stuID = response.data.data.studentID;
                    this.infoForm.image = response.data.data.avatar;
                    this.infoForm.stuName = response.data.data.name;
                    this.infoForm.password = response.data.data.password;
                    this.infoForm.stuSex = response.data.data.sex;
                    this.infoForm.stuCol = response.data.data.college;
                    this.infoForm.stuMaj = response.data.data.major;
                    this.infoForm.stuDorm = response.data.data.dormitory;
                    this.updatecomment(this.infoForm.stuID);
                } 
                else {
                    window.alert(response.data.msg);
                }
            })  
            util.getOrder().then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    console.log(response.data.data);
                    this.orderlist = response.data.data;
                } 
                else {
                    window.alert(response.data.msg);
                }
            }) 
            util.getallOrder().then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    console.log(response.data.data);
                    this.alllist = response.data.data;
                } 
                else {
                    ;
                }
            })  
            
        },
        created(){
            
        }
    }
</script>

<style scoped>
    .clearfloat{clear:both;height:0;font-size:1px;line-height:0px;} 
    .product-image{
        width: 200px;
        height: 200px;
        float: left;
        text-align: center;
        object-fit: cover;
    }
    .border-card {
        width: 60%;
        margin: 5% auto;
        height: 1000px;
        overflow-y: scroll;
    }
    .login-info{
        height:100%;
        width:100%;
        height: 1300px;
        text-align: center;
        font-size: 26px;
        position: relative;
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
    .review-not-found{
        text-align: center;
        color: #1ba078;
        padding: 32px;
    }

    .cart-content-main{
        height: 50px;
        width: 98%;
        margin: 10px;
        line-height: 60px;
        text-align: center;
        border-bottom: 1px dashed #e9eaec;
        overflow: hidden;
        background-color: #DDDDDD;
    }
    .cart-content-main div{
        font-size: 14px;
        line-height: 2em;
        float:left;
    }
    .cart-content-main img{
        width: 80px;
        height: 80px;
        float:left;
        top: 10px;
    }
    .cart-price
    {
        color: #000;
        width:13%;
        height:20px;
        float:left;
        margin-top:20px;
        margin-right:30px;
    }
    .review-price
    {
        color: #000;
        width:28%;
        height:20px;
        float:left;
        margin-top:20px;
        margin-right:30px;
    }
    div.cart-info{
        width: 100%;
        text-align: left;
        float:left;
    }
    .info-img
    {
        color: #000;
        height:20px;
        float:left;
        margin-top:20px;
        margin-left:40px;
    }
</style>