//存储token
export function setToken(token) {
  localStorage.setItem('token', token)
}
// 获取token
export function getToken() {
  return localStorage.getItem('token')
}
// 删除token
export function delToken() {
  localStorage.removeItem('token')
}


// 搭建项目框架 要注意的问题
// 1.我们需要安装哪些依赖来支撑项目的运行   axios  qs   vant
// npm install axios --save
// npm install qs --save
// vue add vant