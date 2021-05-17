<template>
    <div class="login-info">
        <el-tabs type="border-card"  class="border-card" >
        <el-tab-pane label="上传商品">
        <el-form>

            <el-form-item label="商品名称">
              <el-input v-model="itemForm.itemname"></el-input>
            </el-form-item>
            <el-form-item label="商品类别">
              <el-select v-model="itemForm.itemtype"  placeholder="请选择">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="价格">
              <el-input v-model="itemForm.itemprice"></el-input>
            </el-form-item>
            <el-form-item label="邮费">
              <el-input v-model="itemForm.itemfee"></el-input>
            </el-form-item>
            <el-form-item label="描述">
              <el-input v-model="itemForm.itemdes"></el-input>
            </el-form-item>
            <el-form-item label="图片">
              <el-upload
                ref="uploadForm"
                list-type="picture-card"
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
            </el-form-item>
            <el-form-item>
              <el-button @click="uploaditem">点击上传</el-button>
            </el-form-item>
        </el-form>
        </el-tab-pane>
        <el-tab-pane label="我的闲置">
        <MyProd v-for="item in productList" :product="item" :key="item.id"></MyProd>
        <div class="product-not-found"
             v-show="!productList.length">暂无相关商品</div>
        </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import util from '../util';
    import Product from '../components/product.vue';
    import MyProd from '../components/myProd.vue';
    export default {
      components: {Product,MyProd},
        name: "sell",
        data(){
            return {
                itemForm: {
                    itemname: '',
                    itemtype: '',
                    itemprice: '',
                    itemfee: '',
                    itemdes: '',
                    itempic: '',
                },
                options: [{
                  value: '1',
                  label: '电子产品'
                }, 
                {
                  value: '2',
                  label: '图书'
                },
                {
                  value: '3',
                  label: '男士服装'
                },
                {
                  value: '4',
                  label: '女士服装'
                },
                {
                  value: '5',
                  label: '非机动车'
                },
                {
                  value: '6',
                  label: '生鲜水果'
                },
                {
                  value: '7',
                  label: '演艺门票'
                },
                {
                  value: '8',
                  label: '代考/代跑'
                },
                {
                  value: '9',
                  label: '其他闲置'
                },
                ],
                value: '',
                productList:[]
            }
        },
        computed: {
        },
        methods: {
            updateavatar(item){
                this.itemForm.itempic = item.file;
                console.log(this.itemForm.itempic);
            },
            uploaditem()
            {
                const formdata =new FormData(); 

                formdata.append("picture", this.itemForm.itempic);
                formdata.append("name", this.itemForm.itemname);
                formdata.append("type", this.itemForm.itemtype.label);
                formdata.append("price", this.itemForm.itemprice);
                formdata.append("expressFee", this.itemForm.itemfee);
                formdata.append("description", this.itemForm.itemdes);
                console.log(this.itemForm.itemtype.label);
                util.commodities(formdata).then(response => {       
                    let code = response.data.code;
                    if (code === 0) {
                        window.alert("商品上传成功");
                        window.localStorage.setItem("commodityPrice", "");
                        window.localStorage.setItem("commodityName", "");
                        window.localStorage.setItem("commodityPicture", "");
                        window.localStorage.setItem("commodityType", "");
                        window.location.href = '/sell';
                    } 
                    else {
                        console.log(response);
                        window.alert(response.data.msg);
                    }
                })
            },
            
        },
        created(){
            
        },
        mounted(){
            //初始化时通过Vuex actions获取商品列表信息
          util.myCommodity().then(response => {       
                let code = response.data.code;
                if (code == 0) {
                    //console.log(response.data.data);
                    this.productList = response.data.data;
                } 
                else {
                    window.alert(response.data.msg);
                }
            })
          //console.log(window.localStorage.commodityType);
          this.itemForm.itemprice = window.localStorage.getItem("commodityPrice");
          this.itemForm.itemname = window.localStorage.getItem("commodityName");
          this.itemForm.itempic = window.localStorage.getItem("commodityPicture");
          this.itemForm.itemtype = window.localStorage.getItem("commodityType");
        }
    }
</script>

<style scoped>
    .border-card {
    width: 60%;
    margin: 5% auto;
    }
    .login-info{
        height:100%;
        width:100%;
        height:1300px;
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
</style>