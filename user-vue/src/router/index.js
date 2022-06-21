import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/trainNumberList',
    component: () => import('../views/TrainNumberList.vue')
  },

  {
    path: '/news',
    component: () => import('../views/News.vue')
  },
  {
    path: '/routeList',
    component: () => import('../views/RouteList.vue'),
    children: [
      {
        path: '/trainList',
        component: () => import('../views/TrainList.vue'),
      }
    ]
  },
  {
    path: '/queryTrainNumber',
    component: () => import('../views/QueryTrainNumber.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
