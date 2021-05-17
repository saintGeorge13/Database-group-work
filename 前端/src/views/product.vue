<template>
    <div>
        <div class="product">
            <!-- 商品图片、名称、价格 -->
            <div class="product-image">
                <img :src="comForm.comPict">
            </div>
            
            <div class="product-info">
                <el-form>
                <el-form-item label="商品名称">
                    <h2 class="product-cost">{{comForm.comName}}</h2>
                </el-form-item>
                <el-form-item label="价格">
                   <h2 class="product-cost">￥ {{comForm.comPrice}}</h2>
                </el-form-item>
                <el-form-item label="邮价">
                   <h2 class="product-cost">￥ {{comForm.comExp}}</h2>
                </el-form-item>
                <el-form-item label="类型">
                   <h2 class="product-cost"> {{comForm.comType}}</h2>
                </el-form-item>
            
                <el-form-item label="商品描述">
                   <h2 class="product-cost">{{comForm.comDesc}}</h2>
                </el-form-item>

                <el-form-item label="卖家">
                   <h2 class="product-cost">{{comForm.sellerName}}</h2>
                </el-form-item>
                
                
                <el-form-item>
                    <el-button @click="handleAddCart">加入购物车</el-button>
                </el-form-item>
            </el-form>
            </div>
            
        </div>
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
        <Review v-for="item in comForm.commentlist" :comment="item" :key="item.id"></Review> 
            <div class="review-not-found"
                v-show="!comForm.commentlist.length">暂无评论
            </div>
    </div>
</template>

<script>
    
    //导入本地数据
    import Review from '../components/review.vue';
    import util from '../util';
    export default {
        components: {Review},
        computed: {
            // list(){
            //     //从Vuex获取商品列表信息
            //     return this.$store.state.reviewList;
            // }
        },

        data(){
            return {
                //获取路由中的参数
                id: parseInt(this.$route.params.id),
                comForm:{
                    comID:"",
                    comName:"",
                    sellerName:"",
                    comType:"",
                    comPrice:"",
                    comPict:"",
                    comDesc:"",
                    comExp:"",
                    commentlist:"",
                }
            }
        },
        methods: {
            getProduct(){
                setTimeout( () => {
                    this.product = product_data.find(item => item.id === this.id);
                }, 500);
            },
            handleAddCart(){
                util.updateCarts(this.comForm.comID).then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    //console.log(response);
                    window.alert("添加成功");
                } 
                else {
                    window.alert(response.data.msg);
                }
            })
            }
    
        },
        mounted(){
            //初始化数据
            // this.$store.dispatch('getreviewList');
            // this.getProduct();
            util.getCommodity(this.id).then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    console.log(response.data.data);
                    this.comForm.comName = response.data.data.commodityName;
                    this.comForm.comID =  response.data.data.commodityID;
                    this.comForm.comPrice = response.data.data.commodityPrice;
                    this.comForm.comType = response.data.data.commodityType;
                    this.comForm.comExp = response.data.data.commodityExpressFee;
                    this.comForm.comPict = response.data.data.commodityPicture;
                    this.comForm.comDesc = response.data.data.commodityDescription;
                    this.comForm.sellerName = response.data.data.sellerName;
                    this.comForm.commentlist = response.data.data.comments;
                } 
                else {
                    window.alert(response.data.msg);
                }
            })
        }
    }
    
</script>
<!-- scoped属性表示只对当前组件有效，不影响其他组件 -->
<style scoped>
    .product{
        margin: 32px;
        padding: 32px;
        border: 1px solid #dddee1;
        border-radius: 10px;
        overflow: hidden;
        background: linear-gradient(to bottom,rgba(16, 32, 43, 0), rgba(233, 33, 43, 0.5)); /* 标准的语法 */
    }
    .product-image{
        width: 50%;
        height: 450px;
        float: left;
        text-align: center;
    }
    .product-image img{
        height: 100%;
    }
    .product-info{
        width: 50%;
        padding: 0px 0 250px;
        height: 450px;
        float: left;
        text-align: center;
    }
    .product-cost{
        color: #f2352e;
        margin: 8px 0;
    }
    .product-add-cart{
        display: inline-block;
        padding: 8px 64px;
        margin: 8px 0;
        background: #2d8cf0;
        color: #fff;
        border-radius: 4px; 
        cursor: pointer;
    }
    .product-desc{
        background: #fff;
        margin: 32px;
        padding: 32px;
        border: 1px solid #dddee1;
        border-radius: 10px;
        text-align: center;
        background: linear-gradient(to bottom, rgba(233, 33, 43, 0.5),rgba(16, 32, 43, 0)); /* 标准的语法 */
    }
    .product-desc img{
        display: block;
        width: 50%;
        margin: 32px auto;
        padding: 32px;
        border-bottom: 1px solid #dddee1;
    }
    .review-not-found{
        text-align: center;
        padding: 32px;
    }
    .review-not-found{
        text-align: center;
        color: #1ba078;
        padding: 32px;
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
    .cart-content-main{
        height: 80px;
        width: 98%;
        margin: 10px;
        line-height: 60px;
        text-align: center;
        border-bottom: 1px dashed #e9eaec;
        overflow: hidden;
        background-color: #DDDDDD;
    }
    .review-price
    {
        color: #000;
        width:28%;
        height:20px;
        float:left;
        margin-top:20px;
        margin-right:20px;
    }
</style>
