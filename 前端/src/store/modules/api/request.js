import axios from 'axios' //引入 axios
import baseUrl from './baseUrl' //使用环境变量 + 模式的方式定义基础URL
axios.defaults.withCredentials = true;
// 创建 axios 实例
const service = axios.create({
  baseURL: "https://106.55.166.56:8080/api/e-commerce", // api 的 base_url
  timeout: 15000, // 请求超时时间
})
 
export default service