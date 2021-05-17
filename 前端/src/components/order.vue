<template>
    <div class="product">
        <!-- 依次显示商品图片、名称、销量、颜色、单价，加入购物车 -->
        
        <div class="author-order">
            <span class="author-time">{{order.orderDate}}</span>
            <span class="author-name">订单号: {{order.orderID}}</span>
        </div>
            
        <div class="cart-content-main">
            <div class="cart-picture">
            <img :src="order.commodityPicture">
            
            <div class="cart-price">
                 {{order.commodityName}}
            </div>
            <div class="cart-price">
                ￥ {{order.commodityPrice}}
            </div>
            <div class="cart-price">
                {{order.sellerName}}
            </div>
            <div class="cart-price">
                <el-button class="cart-recept"  v-if="order.orderReception === '否'" @click.prevent="handleReceptOrder">确认收货</el-button>
                <a class="cart-recept-ok" v-if="order.orderReception === '是'">收货成功</a>
            </div>
            <div class="cart-price">
                <el-button class="cart-recept" @click.prevent="handlecommentable">进行评价</el-button>
            </div>
            </div>
        </div>
        <div v-if="reviewable">
            <el-rate v-model="star" show-score  text-color="#2b6ae9"></el-rate>
            <h6>评分</h6>
            <el-input class="input" v-model="content" placeholder="请输入内容" maxlength="50" show-word-limit  clearable></el-input>
            <el-button class="cart-recept" size="medium" type="primary" @click.prevent="handlepostcomments">发表评论</el-button>
        </div>
           
    </div>
</template>

<script>
    import util from '../util';
    export default {
        props: {
            order: Object, 
        },
        data(){
                return {
                    star: "",
                    content: "",
                    reviewable: false,
                    
                }
        },
        methods: {
            handleReceptOrder(){
                util.receptOrder(this.order.orderID, '是').then(response => {      
                    let code = response.data.code;
                    if (code == 0) {
                        window.alert("确认收货成功");
                        window.location.href = '/info';
                    } 
                    else {
                        window.alert(response.data.msg);
                    }
                })
            },
            handlecommentable(){
                if(this.reviewable == false)
                    this.reviewable = true;
                else if (this.reviewable == true)
                    this.reviewable = false;
            },
            handlepostcomments(){     
                var objData = {};
                objData["orderID"] = this.order.orderID;
                objData["content"] = this.content;
                objData["star"] = this.star;
                util.postcomments(this.order.orderID, this.content, this.star).then(response => {       
                    let code = response.data.code;
                    if (code == 0) {
                        //console.log(response);
                        window.alert("评论成功");
                        
                        window.location.href = '/info';
                    } 
                    else {
                        window.alert(response.data.msg);
                    }
                })
            }
        }
    }
    
</script>
<style scoped>
    .input{
        width: 90%;
        float: left;
    }
    .product{
        border: 1px solid #dddee1;
        overflow: hidden;
        margin: 10px;
        background: linear-gradient(to bottom,rgba(63, 155, 216, 0.856), rgba(49, 185, 110, 0.5)); /* 标准的语法 */
    }
    .cart{
        margin: 32px;
        height:1200px;
        border: 1px solid #dddee1;
        border-radius: 10px;
        background: linear-gradient(to bottom,rgba(16, 32, 43, 0), rgba(233, 33, 43, 0.5)); /* 标准的语法 */
    }
    .author-order{
        display: inline-block;
        width: 100%;
        height: 20px;
        line-height: 10px;
        
    }   
    .author-name{
        color: #000;
        font-size: 15px;
        float: left;
    }
           
    .author-time{
        color: #000;
        font-size: 15px;
        float: left;
        font-weight: bold;
        margin-right: 20px;
    }
    .talk-box{
        height: 40px;
        line-height: 20px;
        margin: 0 0px;
    }
        
    .reply{
        font-size: 16px;
        color: #000    
    }
    .cart-content-main{
        padding: 0 32px;
        height: 100px;
        line-height: 60px;
        text-align: center;
        border-bottom: 1px dashed #e9eaec;
        overflow: hidden;
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
        width:15%;
        height:20px;
        float:left;
        margin-left:20px;
        margin-top:20px;
    }
    .cart-recept
    {
        color: #000;
        height:20px;
        float:left;
        padding:10px;
        border-radius: 10%;
	    height: 40px; /* 高度 */
	    border-width: 0px; /* 边框宽度 */
	    border-radius: 3px; /* 边框半径 */
	    background: #00DDDD; /* 背景颜色 */
	    cursor: pointer; /* 鼠标移入按钮范围时出现手势 */
	    outline: none; /* 不显示轮廓线 */
	    font-family: Microsoft YaHei; /* 设置字体 */
	    color: white; /* 字体颜色 */
        font-size: 12px; /* 字体大小 */
        font-weight: bold;
    }
    .cart-recept-ok{
        color: #000;
        height:20px;
        float:left;
        padding:10px;
        border-radius: 10%;
	    height: 40px; /* 高度 */
	    border-width: 0px; /* 边框宽度 */
	    border-radius: 3px; /* 边框半径 */
	    background: grey; /* 背景颜色 */
	    cursor: pointer; /* 鼠标移入按钮范围时出现手势 */
	    outline: none; /* 不显示轮廓线 */
	    font-family: Microsoft YaHei; /* 设置字体 */
	    color: white; /* 字体颜色 */
        font-size: 12px; /* 字体大小 */
        font-weight: bold;
    }
    .cart-recept:hover
    {
        background: #5599FF;
    }
    div.cart-picture{
        width: 100%;
        text-align: left;
        float:left;
    }
</style>