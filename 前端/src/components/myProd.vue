<template>
    <div class="product">
        <router-link :to="'/product/' + product.commodityID"
                     class="product-main">
            <!-- 依次显示商品图片、名称、销量、颜色、单价，加入购物车 -->
            <img class="product-image" :src="product.commodityPicture">
            <h4>{{product.commodityName}}</h4>
            <h4>类型:{{product.commodityType}}</h4>
            <div class="product-cost">￥ {{product.commodityPrice}}</div>
            <!-- 阻止冒泡，否则点击按钮的同时也会触发a标签进入详情页 -->
            <div class="product-modify-cart"
                 @click.prevent="handleModify">修改闲置</div>
            <div class="product-delete-cart"
                 @click.prevent="handleDelete">删除闲置</div>
        </router-link>
    </div>
</template>

<script>
    import util from '../util';
    export default {
        props: {
            product: Object
        },
        data () {
            return {
                colors: {
                    '白色': '#ffffff',
                    '金色': '#dac272',
                    '蓝色': '#233472',
                    '红色': '#f2352e'
                }
            }
        },
        methods: {
            handleDelete(){
                util.deleteCommodity(this.product.commodityID).then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    window.alert('删除成功');
                    window.location.href = '/sell';
                } 
                else {
                    window.alert(response.data.msg);
                }
                })
            },
            handleModify(){
                
                    window.localStorage.setItem("commodityPrice", this.product.commodityPrice);
                    window.localStorage.setItem("commodityName", this.product.commodityName);
                    window.localStorage.setItem("commodityPicture", this.product.commodityPicture);
                    window.localStorage.setItem("commodityType", this.product.commodityType);
                    //localStorage.this.$store.commit('modifycommodity', this.product);  
                    //console.log(window.localStorage.getItem("commodityID"));
                    self.location = '/sell';
                
                
                
            }
        },
        mounted(){
            console.log(this.$store.state.modifycommodity);
        }
    }
</script>
<!-- scoped属性表示只对当前组件有效，不影响其他组件 -->
<style scoped>
    .product-image{
        
        height: 200px;
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
    .product-modify-cart{
        display: none;
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
    .product-delete-cart{
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
    .product-main:hover .product-delete-cart{
        display: inline-block;
    }
    .product-main:hover .product-modify-cart{
        display: inline-block;
    }
</style>