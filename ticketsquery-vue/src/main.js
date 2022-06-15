// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import DataTables from 'vue-data-tables'

axios.defaults.baseURL = 'http://localhost:8888'

Vue.prototype.axios = axios
Vue.use(VueAxios, axios)
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(DataTables)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  mode: 'history',
  router,
  components: { App },
  template: '<App/>'
})
