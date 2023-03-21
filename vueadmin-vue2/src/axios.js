import axios from "axios";
import router from "./router";
import Element from "element-ui"

axios.defaults.baseURL = "http://localhost:8081"

const request = axios.create({
    timeout: 5000,  //5秒
    headers: {
        'Content-Type': 'application/json;charset=utf-8'   //json数据
    }
})
//请求前捕获token
request.interceptors.request.use(config => {
    config.headers['Authorization'] = localStorage.getItem("token")
    return config
})

//请求后捕获状态码
request.interceptors.response.use(response => {
    console.log("response->" + response)
    let res = response.data
    console.log(res)
    if (res.code === 200) {
        return response
    } else {
        Element.Message.error(res.msg ? res.msg : '系统异常')
        return Promise.reject(response.data.msg)
    }
}, error => {
    if (error.response.data) {
        error.massage = error.response.data.msg
    }
    if (error.response.status === 401) {
        router.push("/login")
    }
    Element.Message.error(error.massage, {duration: 3000})

    return Promise.reject(error)
})
export default request