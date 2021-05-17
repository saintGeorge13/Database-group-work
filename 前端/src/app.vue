<template>
    <div class="caidan">
        <template v-if="show">
            <el-menu default-active="1" class="el-menu-demo" mode="horizontal" background-color="#40E0D0" text-color="#fff">
                <el-menu-item index="1">
                    <router-link to="/list" class="header-title">电商网站首页
                    </router-link>
                </el-menu-item>
                <el-menu-item index="2">
                    <router-link to="/sell" class="header-title">我要卖宝贝
                    </router-link>
                </el-menu-item>
                
                <el-menu-item index="6">
                    <el-input v-model="search"  @focus="focus" @blur="blur" @keyup.enter.native="searchHandler" placeholder="搜索商品">
                        <el-button slot="append" icon="el-icon-search" id="search" @click="searchHandler"></el-button>
                    </el-input>
                        <!---设置z-index优先级,防止被走马灯效果遮挡-->
                        <el-card @mouseenter="enterSearchBoxHandler" v-if="isSearch"    class="box-card"  id="search-box"  style="position:relative;z-index:15" >
                        <dl v-if="isHistorySearch">
                            <dt class="search-title" v-show="history">历史搜索</dt>
                            <dt class="remove-history" v-show="history" @click="removeAllHistory">
                            <i class="el-icon-delete"></i>清空历史记录
                            </dt>
                            <el-tag
                            v-for="search in historySearchList"
                            :key="search.id"
                            closable
                            :type="search.type"
                            @close="closeHandler(search)"
                            style="margin-right:5px; margin-bottom:5px;"
                            >{{search.name}}</el-tag>
                            <dt class="search-title">热门搜索</dt>
                            <dd v-for="search in hotSearchList" :key="search.id">{{search}}</dd>
                        </dl>
                        <dl v-if="isSearchList">
                            <dd v-for="search in searchList" :key="search.id">
                                <router-link :to="'/product/' + search.commodityID"
                                    class="product-main">
                                    {{search.commodityName}}
                                </router-link>
                            </dd>
                            <dd v-show="!searchList.length">暂无数据</dd>
                        </dl>
                        </el-card>
                </el-menu-item>
                
                <div class="header-menu">
                    <el-menu-item index="3">
                        <router-link to="/cart" class="header-menu-cart">购物车
                        </router-link>
                    </el-menu-item>    
                </div>
                <div class="header-menu">
                <el-menu-item index="4">
                    <router-link to="/info" class="header-menu-cart">欢迎{{user}}
                    </router-link>
                </el-menu-item>
                </div>
                <div class="header-menu">
                    <el-menu-item index="5">
                    <router-link to="/login/logout" class="header-menu-cart">
                        退出登录
                    </router-link></el-menu-item>                  
                </div>
            </el-menu>
        </template>
        <!-- 挂载所有路由 -->
        <router-view></router-view>
    </div>
</template>

<script>
import RandomUtil from "./search/randomUtil";
import Store from "./search/store";
import util from "./util";
    export default {
        data(){
            return {
                templist: [],
                search: "", //当前输入框的值
                isFocus: false, //是否聚焦
                hotSearchList: ["创新分讲座票", "德育分讲座票", "马克思论文代写", "体测代跑"], //热门搜索数据
                historySearchList: [], //历史搜索数据
                searchList: [], //搜索返回数据,
                history: false,
                types: ["", "success", "info", "warning", "danger"],//搜索历史tag式样
                user: this.$store.state.username,
               
            }
        },
        methods: {
            focus() {
                this.isFocus = true;
                this.historySearchList = Store.loadHistory() == null ? [] : Store.loadHistory();
                this.history = this.historySearchList.length == 0 ? false : true;
            },
            blur() {
                var self = this;
                this.searchBoxTimeout = setTimeout(function() {
                    self.isFocus = false;
                }, 300);
            },
            enterSearchBoxHandler() {
                clearTimeout(this.searchBoxTimeout);
            },
            searchHandler() {
            //随机生成搜索历史tag式样
            util.searchcommodity(this.search).then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    this.searchList = response.data.data;
                    console.log(this.searchList);
                    
                }
                })
                let n = RandomUtil.getRandomNumber(0, 5);
                let exist =
                    this.historySearchList.filter(value => {
                    return value.name == this.search;
                    }).length == 0
                    ? false
                    : true;
                if (!exist) {
                    this.historySearchList.push({ name: this.search, type: this.types[n] });
                    Store.saveHistory(this.historySearchList);
                }
                this.history = this.historySearchList.length == 0 ? false : true;
                
            },
            closeHandler(search) {
                this.historySearchList.splice(this.historySearchList.indexOf(search), 1);
                Store.saveHistory(this.historySearchList);
                clearTimeout(this.searchBoxTimeout);
                if (this.historySearchList.length == 0) {
                    this.history = false;
                }
            },
            removeAllHistory() {
                Store.removeAllHistory();
            },
            
            
        },
        
        computed: {
        
            show(){
                return this.$store.state.loginStatus;
            },
            isHistorySearch() {
                return this.isFocus && !this.search;
            },
            isSearchList() {
                return this.isFocus && this.search;
            },
            isSearch() {
                return this.isFocus;
            }
        }     
        
    }
</script>

<style scoped>
.caidan{
    width: 1525px;
    height: 200px;
}
.left {
  margin-left: 200px;
}
.center {
  margin-top: 15px;
  margin-left: 200px;
}
#search {
  background-color: #23a2b36c;
  border-radius: 0%;
}
.search-title {
  color: #bdbaba;
  font-size: 15px;
  margin-bottom: 5px;
}
.remove-history {
  color: #bdbaba;
  font-size: 15px;
  float: right;
  margin-top: -22px;
}
#search-box {
  width: 555px;
  height: 300px;
  margin-top: 0px;
  padding-bottom: 20px;
}
</style>