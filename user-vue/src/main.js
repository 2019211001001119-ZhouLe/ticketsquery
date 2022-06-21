import Vue from 'vue'
import App from './App.vue'
import router from './router'
import * as echarts from "echarts";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Axios from "axios"
import VueAxios from 'vue-axios'
import qs from 'qs'
import dataV from '@jiaminghi/data-view'


Vue.use(dataV)
Vue.prototype.$qs=qs
// 开启axios
Vue.use(VueAxios, Axios);
// 把axios挂载到vue原型对象中
Vue.prototype.myAxios = Axios;
Axios.defaults.baseURL="http://localhost:8888";
Vue.use(ElementUI);
Vue.prototype.$echarts = echarts;
// 引⼊moment
import moment from 'moment'
// 全局注册
Vue.filter("fmtDate_global", function (date) {
 return moment(date).format("YYYY-MM-DD HH:mm:ss");
 // 或者return⾃⼰编写的时间处理函数
})
Vue.config.productionTip = false
new Vue({
 router,
 render: h => h(App)
}).$mount('#app')
