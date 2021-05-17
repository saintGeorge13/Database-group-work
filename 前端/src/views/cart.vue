<template>
    <div class="cart">
        <div class="cart-header">
            <div class="cart-header-title">购物清单</div>
            <div class="cart-header-main">
                <div class="cart-info">商品信息</div>
                <div class="cart-price">卖家名称</div>
                <div class="cart-price">单价</div>
                <div class="cart-cost">邮费</div>
                <div class="cart-count">小计</div>
                <div class="cart-delete">删除</div>

            </div>
        </div>
        <div class="cart-content">
            <!-- 列表显示购物清单 -->
            <div class="cart-content-main" v-for="item in cartList" :key="item">
                <div class="cart-info">
                    <img :src="item.commodityPicture">
                    <span>{{item.commodityName}}</span>
                </div>

                <div class="cart-price">
                     {{item.sellerName}}
                </div>

                <div class="cart-price">
                    ￥ {{item.commodityPrice}}
                </div>
                
                <div class="cart-cost">
                    ￥ {{item.commodityExpressFee}}
                </div>



                <div class="cart-count">
                    ￥ {{item.commodityPrice + item.commodityExpressFee}}
                </div>

                <div class="cart-delete">
                    <span class="cart-control-delete"
                          @click="handleDelete(item)">删除</span>
                </div>
            </div>
            <div class="cart-empty" v-if="!cartList.length">购物车为空</div>
        </div>
        
        <div class="cart-footer" v-show="cartList.length">
            <div class="cart-footer-desc">
                共计 <span>{{countAll}}</span>
            </div>
            <div class="cart-footer-desc">
                应付总额 <span>{{costAll - promotion}}</span>
                <br>
                <template v-if="promotion">
                    (优惠<span>￥ {{promotion}} </span>)
                </template>
            </div>
            <div class="cart-footer-desc">
                <div class="cart-control-order"
                     @click="handleOrder">现在结算</div>
            </div>
        </div>
    </div>
</template>

<script>
    
    import util from '../util';
    export default {
        name: "cart",
        data(){
            return {
                promotion: 0,
                promotionCode: '',
                cartList:[]
            }
        },
        computed: {
            //购物车数据
            // cartList(){
            //     console.log(this.$store.state.cartList);
            //     return this.$store.state.cartList;
            // },
            
            //购物车商品总数
            countAll(){
                return this.cartList.length;
            },
            //购物车商品总价
            costAll(){
                let cost = 0;
                this.cartList.forEach(item => {
                    cost += item.commodityPrice + item.commodityExpressFee;
                });
                return cost;
            }
        },
        methods: {
            //通知Vuex,完成下单
            handleOrder(){
                this.cartList.forEach(item => {
                    this.updateOrder(item);
                });
                this.cartList.forEach(item => {
                    this.handleDelete(item);
                });
                window.alert("购买成功");
            },
            
            updateOrder(item){
                util.updateOrder(item.commodityID).then(response => {       
                let code = response.data.code;
                if (code == 0) {
                } 
                else {
                    window.alert(response.data.msg);
                }
            })
            },
            //根据index查找商品id进行删除
            handleDelete(item){
                util.deleteCarts(item.cartID).then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    this.refresh();
                } 
                else {
                    window.alert(response.data.msg);
                }
                })
            },
            refresh(){
                console.log("wocao");
                util.getCarts().then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    this.cartList = response.data.data;
                } 
                else {
                    window.alert(response.data.msg);
                }
                })
            }
        },
        mounted(){
            util.getCarts().then(response => {       
                let code = response.data.code;
                console.log(response);
                if (code == 0) {
                    this.cartList = response.data.data;
                } 
                else {
                    window.alert(response.data.msg);
                }
            })
        }
    }
</script>

<style scoped>
    .cart{
        margin: 32px;
        height:1200px;
        border: 1px solid #dddee1;
        border-radius: 10px;
        background: linear-gradient(to bottom,rgba(16, 32, 43, 0), rgba(233, 33, 43, 0.5)); /* 标准的语法 */
    }
    .cart-header-title{
        padding: 16px 32px;
        border-bottom: 1px solid #dddee1;
        border-radius: 10px 10px 0 0;
        background: #f8f8f9;
    }
    .cart-header-main{
        padding: 8px 32px;
        overflow: hidden;
        border-bottom: 1px solid #dddee1;
        background: #eee;
        overflow: hidden;
    }
    .cart-empty{
        text-align: center;
        padding: 32px;
    }
    .cart-header-main div{
        text-align: center;
        float: left;
        font-size: 14px;
    }
    div.cart-info{
        width: 50%;
        text-align: left;
    }
    .cart-price{
        width: 10%;
    }
    .cart-count{
        width: 10%;
    }
    .cart-cost{
        width: 10%;
    }
    .cart-delete {
        width: 10%;
    }
    .cart-content-main{
        padding: 0 32px;
        height: 60px;
        line-height: 60px;
        text-align: center;
        border-bottom: 1px dashed #e9eaec;
        overflow: hidden;
    }
    .cart-content-main div{
        float: left;
    }
    .cart-content-main img{
        width: 40px;
        height: 40px;
        position: relative;
        top: 10px;
    }
    .cart-control-minus,
    .cart-control-add{
        display: inline-block;
        margin: 0 4px;
        width: 24px;
        height: 24px;
        line-height: 22px;
        text-align: center;
        background: #f8f8f9;
        border-radius: 50%;
        box-shadow: 0 1px 1px rgba(0,0,0,.2);
        cursor: pointer;
    }
    .cart-control-delete{
        cursor: pointer;
        color: #2d8cf0;
    }
    .cart-promotion{
        padding: 16px 32px;
    }
    .cart-control-promotion,
    .cart-control-order{
        display: inline-block;
        padding: 8px 32px;
        border-radius: 6px;
        background: #2d8cf0;
        color: #fff;
        cursor: pointer;
    }
    .cart-control-promotion{
        padding: 2px 6px;
        font-size: 12px;
        border-radius: 3px;
    }
    .cart-footer{
        padding: 32px;
        text-align: right;
    }
    .cart-footer-desc{
        display: inline-block;
        padding: 0 16px;
    }
    .cart-footer-desc span{
        color: #f2352e;
        font-size: 20px;
    }
</style>