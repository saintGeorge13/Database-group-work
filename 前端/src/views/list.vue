<template>
        <div>
        <Product v-for="item in list" :product="item" :key="item.comID" ></Product> 
            <div class="product-not-found"
                v-show="!list.length">暂无相关商品</div>
        </div>
</template>

<script>
    //导入商品简介组件
    import Product from '../components/product.vue';
    import util from '../util';
    export default {
        components: {Product},
        computed: {
            // list(){
            //     //从Vuex获取商品列表信息
            //     return this.$store.state.productList;
            // },
            brands(){
                return this.$store.getters.brands;
            },
            colors(){
                return this.$store.getters.colors;
            },
        },
        data(){
            return {
                //种类过滤
                filterType:'',
                types: [],
                filterBrand: '',
                filterColor: '',
                order: '',
                list:[],
                usertype: true,
                alllist: []
            }
        },
        methods: {
            //种类筛选
            handleFilterType(type){
                if(this.filterType === type){
                    this.filterType = '';
                }else{
                    this.filterType = type;
                }
            },
            judgeFilterType(commodityType){
                if(commodityType === filterType){
                    return true;
                }else{
                    return false;
                }
            },
            //品牌筛选
            handleFilterBrand(brand){
                //点击品牌过滤，再次点击取消
                if (this.filterBrand === brand) {
                    this.filterBrand = '';
                }else{
                    this.filterBrand = brand;
                }
            },
            //颜色筛选
            handleFilterColor(color){
                //点击颜色过滤，再次点击取消
                if (this.filterColor === color) {
                    this.filterColor = '';
                }else{
                    this.filterColor = color;
                }
            },
            handleOrderDefault(){
                this.order = '';
            },
            handleOrderSales(){
                this.order = 'sales';
            },
            handleOrderCost(){
                //当点击升序时将箭头更新↓,降序设置为↑
                if(this.order === 'cost-desc'){
                    this.order = 'cost-asc';
                }else{
                    this.order = 'cost-desc';
                }
            },

        },
        mounted(){
            console.log(this.usertype);
            util.admin_commodities().then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    this.alllist = response.data.data;
                    console.log(this.alllist);
                } 
                else {
                    ;
                }
            }),

            
            util.home().then(response => {     
                
                let code = response.data.code;
                if (code == 0) {
                    this.list = response.data.data;
                    console.log(this.list);
                } 
                else {
                    window.alert(response.data.msg);
                }
                
            })
            ,
            util.getCommodityTypes().then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    this.types = response.data.data;
                    //console.log(this.list);
                } 
                else {
                    window.alert(response.data.msg);
                }
            })
        }
    }
</script>

<style scoped>
    .list-control{
        border-radius: 6px;
   
        padding: 10px;
        box-shadow: 0 1px 1px rgba(76, 86, 184, 0.2);
        width: 1500px;
        height: 200px;
        font-size: 20px;
        right: 0;
        color: rgb(160, 175, 196);
        background: -webkit-linear-gradient( left, rgba(0, 0, 0, 0),rgba(0, 0, 0, 0.2) ); /* Safari 5.1 - 6 */
        background: -o-linear-gradient(right,rgba(0, 0, 0, 0),rgba(0, 0, 0, 0.2)); /* Opera 11.1 - 12*/
        background: -moz-linear-gradient( right, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.2)); /* Firefox 3.6 - 15*/
        background: linear-gradient(to right,rgba(88, 62, 219, 0.945), rgba(233, 33, 43, 0.5)); /* 标准的语法 */
    }
    .list-control-filter{
        margin-bottom: 16px;
    }
    .list-control-filter-item,
    .list-control-order-item {
        cursor: pointer;
        display: inline-block;
        border: 1px solid #e9eaec;
        border-radius: 10px;
        margin-right: 6px;
        padding: 20px 30px;
    }
    .list-control-filter-item.on,
    .list-control-order-item.on{
        background: #f2352e;
        border: 1px solid #f2352e;
        color: #fff;
    }
    .product-not-found{
        text-align: center;
        padding: 32px;
    }
   
</style>