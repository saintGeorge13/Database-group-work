import request from './store/modules/api/request' //引入封装好的 axios 请求
 


export default {
    //去除前后空格
    trim(str){
        return str.replace(/^\s*(.*?)\s*$/g, '$1');
    },
    //数组去重
    getFilterArray(array){
        const set = new Set(array);
        return [...set];
    },
    login(studentID, password) { //登录接口
        return request({ //使用封装好的 axios 进行网络请求
          url: '/login',
          method: 'post',
          data: { //提交的数据
            studentID,
            password
          }
        })
    },
    admin_login(account, password){
      return request({ //使用封装好的 axios 进行网络请求
        url: '/admin_login',
        method: 'post',
        data: { //提交的数据
          account,
          password
        }
      })
    },
    register(user){
      return request({ //使用封装好的 axios 进行网络请求
        url: '/register',
        method: 'post',
        data: user
        
      })
    },
    registerCode(mailbox){
      return request({ //使用封装好的 axios 进行网络请求
        url: '/registerCode?mailbox='+mailbox,
        method: 'get',
        data: mailbox,
      })
    },
    info(username) { //登录接口
        return request({ //使用封装好的 axios 进行网络请求
          url: '/info',
          method: 'get',
          data: { //提交的数据
            username
          }
        })
    },

    user() { //用户信息接口
      return request({ //使用封装好的 axios 进行网络请求
        url: '/user',
        method: 'get',
        data: { //提交的数据
        }
      })
    },
    modifyuser(user) { //用户信息修改
      return request({ //使用封装好的 axios 进行网络请求
        url: '/user',
        method: 'put',
        data: user,
      })
    },
    order() { 
      return request({ //使用封装好的 axios 进行网络请求
        url: '/order',
        method: 'get',
        data: {
        }
      })
    },
    comment(sellerID) { 
      return request({ //使用封装好的 axios 进行网络请求
        url: '/comments?sellerID='+sellerID,
        method: 'get',
        data: {
          sellerID
        }
      })
    },
    postcomments(orderID, content, star){
      return request({ //使用封装好的 axios 进行网络请求
        url: '/comments',
        method: 'post',
        data: {
          orderID,
          content,
          star
        }
      })
    },
  home() { 
    return request({ //使用封装好的 axios 进行网络请求
      url: '/random_commodities',
      method: 'get',
      data: { //提交的数据
      }
    })
  },
  admin_commodities() { 
    return request({ //使用封装好的 axios 进行网络请求
      url: '/admin_commodities',
      method: 'get',
      data: { //提交的数据
      }
    })
  },
  commodity(username) { //登录接口
    return request({ //使用封装好的 axios 进行网络请求
      url: '/commodity',
      method: 'post',
      data: { //提交的数据
        username
      }
    })
  },

  getCarts() { 
    return request({ //使用封装好的 axios 进行网络请求
      url: '/carts',
      method: 'get',
      data: { 
      }
    })
  },

  updateCarts(commodityID) { 
    return request({ //使用封装好的 axios 进行网络请求
      url: '/carts',
      method: 'post',
      data: { 
        commodityID
      }
    })
  },

  deleteCarts(cartID) { 
    return request({ //使用封装好的 axios 进行网络请求
      url: '/carts/'+cartID,
      method: 'delete',
      data: { 
      }
    })
  },

  getCommodityTypes() { 
    return request({ //使用封装好的 axios 进行网络请求
      url: '/commodity_types',
      method: 'get',
      data: { 
        
      }
    })
  },

  getCommodity(commodityID) { 
    return request({ //使用封装好的 axios 进行网络请求
      url: '/commodities/'+commodityID,
      method: 'get',
      data: { 
      }
    })
  },

  deleteCommodity(commodityID) { 
    return request({ //使用封装好的 axios 进行网络请求
      url: '/commodities/'+commodityID,
      method: 'delete',
      data: { 
      }
    })
  },
  delete_commodities(commodityID){
    return request({ //使用封装好的 axios 进行网络请求
      url: '/admin_commodities/'+commodityID,
      method: 'delete',
      data: { 
      }
    })
  },
  updateOrder(commodityID){
    return request({ //使用封装好的 axios 进行网络请求
      url: '/orders',
      method: 'post',
      data: { 
        commodityID
      }
    })
  },
  receptOrder(orderID, reception){
    return request({ //使用封装好的 axios 进行网络请求
      url: '/orders',
      method: 'put',
      data: { 
        orderID,
        reception
      }
    })
  },

  getOrder(){
    return request({ //使用封装好的 axios 进行网络请求
      url: '/orders',
      method: 'get',
      data: { 
      }
    })
  },
  getallOrder(){
    return request({ //使用封装好的 axios 进行网络请求
      url: '/admin_orders',
      method: 'get',
      data: { 
      }
    })
  },

  myCommodity(){
    return request({ //使用封装好的 axios 进行网络请求
      url: '/my_commodities',
      method: 'get',
      data: { 
      }
    })
  },

  avatar(avatar) { //头像
    return request({ //使用封装好的 axios 进行网络请求
      url: '/avatar',
      method: 'put',
      data: { //提交的数据
        avatar
      },
      headers: {
        "Content-Type": "multipart/form-data;",
      },
      transformRequest: [
        function () {
          return avatar;
        },
      ],
    })
  },
  commodities(commodity){
    return request({ //使用封装好的 axios 进行网络请求
      url: '/commodities',
      method: 'post',
      data: commodity,
      headers: {
        "Content-Type": "multipart/form-data;",
      },
      transformRequest: [
        function () {
          return commodity;
        },
      ],
    },
    )
    
  },
  searchcommodity(keyword){
    return request({ //使用封装好的 axios 进行网络请求
      url: '/commodities?keyword='+keyword,
      method: 'get',
      data: keyword,
    })
  }
  ,
  admindelete(commodityID){
    return request({ //使用封装好的 axios 进行网络请求
      url: '/admin_commodities/'+commodityID,
      method: 'delete',
      data: { 
      }
    })
  }
}