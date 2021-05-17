<template>
    <el-container class="product">
        <router-link :to="'/product/' + product.commodityID"
                     class="product-main">
            <!-- 依次显示商品图片、名称、销量、颜色、单价，加入购物车 -->
            <img class="product-image" :src="product.commodityPicture">
            <h4>{{product.commodityName}}</h4>
            <h4>类型:{{product.commodityType}}</h4>
            <div class="product-cost">￥ {{product.commodityPrice}}</div>
            <!-- 阻止冒泡，否则点击按钮的同时也会触发a标签进入详情页 -->
            <div class="product-add-cart"
                 @click.prevent="handleAddCart">加入购物车</div>
            <div v-if="usertype === 'false'" class="product-delete-cart"
                 @click.prevent="deleteAddCart">删除商品</div>   
        </router-link>
    </el-container>
</template>

<script>
    import util from '../util';
    export default {
        props: {
            product: Object
        },
        data () {
            return {
                usertype: this.$store.state.usertype,
                colors: {
                    '白色': '#ffffff',
                    '金色': '#dac272',
                    '蓝色': '#233472',
                    '红色': '#f2352e'
                }
            }
        },
        methods: {
            deleteAddCart(){
                //console.log(this.product.commodityID);
                util.admindelete(this.product.commodityID).then(response => {       
                console.log(response);
                let code = response.data.code;
                if (code == 0) {
                    window.alert('删除成功');
                    window.location.href = '/list';
                } 
                else {
                    window.alert(response.data.msg);
                }
                })
            },
            handleAddCart(){
                util.updateCarts(this.product.commodityID).then(response => {       
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
        
        
    }
</script>
<!-- scoped属性表示只对当前组件有效，不影响其他组件 -->
<style scoped>
    .product-image{
        
        height: 300px;
        text-align: center;
        object-fit: cover;
    }
    .product{
        width: 25%;
        float: left;
        background: linear-gradient(to right,rgba(96, 170, 142, 0.945), rgba(170, 207, 83, 0.5)); /* 标准的语法 */
    }
    .product-main{
        display: block;
        margin: 16px;
        padding: 16px;
        border: 1px solid #dddee1;
        border-radius: 6px;
        overflow: hidden;
        background: #fff;
        text-align: center;
        position: relative;
    }
    .product-main img{
        width: 100%;
    }
    h4{
        color: #222;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .product-main:hover h4{
        color: #0070c9;
    }
    .product-color{
        display: block;
        width: 16px;
        height: 16px;
        border: 1px solid #dddee1;
        border-radius: 50%;
        margin: 6px auto;
    }
    .product-cost{
        color: #de4037;
        margin-top: 6px;
    }
    .product-add-cart{
        display: none;
        padding: 4px 8px;
        background: #2d8cf0;
        color: #fff;
        font-size: 12px;
        border-radius: 3px;
        cursor: pointer;
        position: absolute;
        top: 5px;
        right: 5px;
    }
    .product-delete-cart{
        padding: 4px 8px;
        background: #2d8cf0;
        color: #fff;
        font-size: 12px;
        border-radius: 3px;
        cursor: pointer;
        position: absolute;
        top: 5px;
        left: 5px;
    }
    .product-main:hover .product-add-cart{
        display: inline-block;
    }
</style>