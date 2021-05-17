//商品列表路由配置
const routers = [
    {
        path: '/list',
        meta: {
            title: '商品列表'
        },
        component: (resolve) => require(['./views/list.vue'], resolve)
    },
    {
        path: '/product/:id',
        meta: {
            title: '商品详情'
        },
        component: (resolve) => require(['./views/product.vue'], resolve)
    },
    {
        path: '/cart',
        meta: {
            title: '购物车'
        },
        component: (resolve) => require(['./views/cart.vue'], resolve)
    },
    {
        path: '/login/:loginStatus',
        meta: {
            title: '登录注册'
        },
        component: (resolve) => require(['./views/login.vue'], resolve)
    },
    {
        path: '/sell',
        meta: {
            title: '出售'
        },
        component: (resolve) => require(['./views/sell.vue'], resolve)
    },
    {
        path: '/info',
        meta: {
            title: '个人信息'
        },
        component: (resolve) => require(['./views/info.vue'], resolve)
    },
    {
        path: '*',
        redirect: '/login/login'
    }
];
export default routers;
