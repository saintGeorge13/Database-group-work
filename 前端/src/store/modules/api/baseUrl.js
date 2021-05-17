let baseUrl = "";
switch (process.env.NODE_ENV) { //注意变量名是自定义的
    case 'dev':
        //baseUrl = "http://localhost:8088/"  //开发环境url
        baseUrl = "http://106.55.166.56:8080/"
        break
    case 'serve':
        baseUrl = "http://localhost:8089/"   //生产环境url
        break
}

export default baseUrl;