import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import dataV from '@jiaminghi/data-view'
import axios from 'axios'
axios.defaults.baseURL = 'http://localhost:8888'

Vue.use(dataV)
Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.axios = axios

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
