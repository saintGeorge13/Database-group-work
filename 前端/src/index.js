import Vue from 'vue';
import VueRouter from 'vue-router';
import Routers from './router';
import Vuex from 'vuex';
import App from './app.vue';
import './style.css';
import product_data from './product';
import review_data from './review';
import order_data from './order';
import util from './util';
import ElementUI from 'element-ui'
//import user from './store/modules/user' //引入 user module

import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)
Vue.use(VueRouter);
Vue.use(Vuex);

//路由配置
const RouterConfig = {
    //使用H5 history模式
    mode: 'history',
    routes: Routers
};

const router = new VueRouter(RouterConfig);

//跳转前设置title
router.beforeEach((to, from, next) => {
    window.document.title = to.meta.title;
    next();
});
//跳转后设置scroll为原点
router.afterEach((to, from, next) => {
    window.scrollTo(0, 0);
});

//配置Vuex状态管理
const store = new Vuex.Store({
    state: {
        //商品列表信息
        productList: [],
        reviewList: [],
        orderList: [],
        //购物车数据，数组形式，数据元素为对象（商品id，购买数量count）
        cartList: [],
        //当前用户账号
        username: window.localStorage.getItem('username'),
        //登录状态
        loginStatus: true,
        modifycommodity: [],
        usertype: window.localStorage.getItem('usertype'),
        
    },
    
    getters: {
        //品牌、颜色筛选
        brands: state => {
            const brands = state.productList.map(item => item.brand);
            return util.getFilterArray(brands);
        },
        colors: state => {
            const colors = state.productList.map(item => item.color);
            return util.getFilterArray(colors);
        }
    },
    //mutations只能以同步方式
    mutations: {
        modifycommodity(state, product){
            state.modifycommodity = product;
        },
        //添加商品列表
        setProductList(state, data){
            state.productList = data;
        },
        setreviewList(state, data){
            state.reviewList = data;
        },
        setorderList(state, data){
            state.orderList = data;
        },
        //添加购物车
        addCart(state, product){
            const isAdded = state.cartList.find(item => item.product.commodityName === product.commodityName);
            //console.log(state.cartList);
            //如果不存在设置购物车为1，存在count++
            if(isAdded){
                isAdded.count++;
            }else{
                state.cartList.push({
                    product: product,
                    count: 1,  
                })
            }
        },
        //修改购物车商品数量
        editCartCount(state, payload){
            const product = state.cartList.find(item => item.id === payload.id);
            product.count += payload.count;
        },
        //删除购物车商品
        deleteCart(state, product){
            const index = state.cartList.findIndex(item => item.product.commodityName === product.commodityName);
            state.cartList.splice(index, 1)
        },
        //清空购物车
        emptyCart(state){
            state.cartList = [];
        },
        getUser(state, username){
            console.log('username',username)
            state.username = username;
        },
        setUser(state, data){
            state.username = data;
        },
        setLoginStatus(state, flag){
            state.loginStatus = flag;  
        },
        setUserType(state, data){
            state.usertype = data;
        }
        
    },
    actions: {
        //异步请求商品列表，暂且使用setTimeout
        getProductList(context){
            setTimeout(() => {
                context.commit('setProductList', product_data)
            }, 500);
        },
        getreviewList(context){
            setTimeout(() =>{
                context.commit('setreviewList', review_data)
            }, 500);
        },
        getorderList(context){
            setTimeout(() =>{
                context.commit('setorderList', order_data)
            }, 500);
        },
        //购买
        buy(context){
            //生产环境使用ajax请求服务端响应后再清空购物车
            return new Promise(resolve => {
                setTimeout(() => {
                    context.commit('emptyCart');
                    resolve();
                }, 500);
            });
        },
    }
});
const app = new Vue({
    el: '#app',
    router,
    store,
    render: h => {
        return h(App)
    }
})


