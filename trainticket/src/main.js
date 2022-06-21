import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import dataV from '@jiaminghi/data-view'
import axios from 'axios'
import VueAxios from 'vue-axios'
import DataTables from 'vue-data-tables'
axios.defaults.baseURL = 'http://localhost:8888'

Vue.use(dataV)
Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.axios = axios
Vue.use(VueAxios, axios)
Vue.use(DataTables)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
