import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '../views/LoginView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/manage',
    name: 'manage',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Manage.vue'),
    children:[
      // 首页
      {
        path:'home',
        component: () => import('../views/Home/HomeView.vue')
      },
      {
        path:'traintype',
        component: () => import('../views/TrainNumber/TrainType.vue')
      },
      {
        path:'traininfo',
        component: () => import('../views/TrainNumber/TrainInfo.vue')
      },
      {
        path:'detailsquery',
        component: () => import('../views/TrainNumber/DetailsQuery.vue')
      },
      {
        path:'potmanage',
        component: () => import('../views/Pot/PotManage.vue')
      },
      {
        path:'newsmody',
        component: () => import('../views/News/NewsMody.vue')
      },
      {
        path:'newsissue',
        component: () => import('../views/News/NewsDetail.vue')
      },
      {
        path:'manageradd',
        component: () => import('../views/Manager/ManagerAdd.vue')
      },
      {
        path:'managerinfo',
        component: () => import('../views/Manager/ManagerInfo.vue')
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
