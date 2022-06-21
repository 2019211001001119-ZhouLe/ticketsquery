import Vue from 'vue'
import Router from 'vue-router'
import HomeView from '@/components/HomeView'
import TrainStation from '@/components/TrainStation'
import Train from '@/components/Train'
import TrainNumberQuery from '@/components/TrainNumberQuery'
import TrainNumber from '@/components/TrainNumber'
import DetailsQuery from '@/components/DetailsQuery'
import Details from '@/components/Details'
import TrainType from '@/components/TrainType'
import Manage from '@/components/Manage'
import LoginView from '@/components/LoginView'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'userHome',
      component: () => import('../components/User/userHome'),
    },
    {
      path: '/trainNumberList',
      component: () => import('../components/User/TrainNumberList')
    },

    {
      path: '/news',
      component: () => import('../components/User/News')
    },
    {
      path: '/routeList',
      component: () => import('../components/User/RouteList'),
      children: [
        {
          path: '/trainList',
          component: () => import('../components/User/TrainList'),
        }
      ]
    },
    {
      path: '/queryTrainNumber',
      component: () => import('../components/User/QueryTrainNumber')
    },
    {
      path: '/',
      name: 'LoginView',
      component: LoginView
    },
    {
      path: '/homeview',
      name: 'HomeView',
      component: HomeView
    },
    {
      path: '/train',
      name: 'Train',
      component: Train
    },
    {
      path: '/trainstation',
      name: 'TrainStation',
      component: TrainStation
    },
    {
      path: '/trainnumber',
      name: 'TrainNumber',
      component: TrainNumber
    },
    {
      path: '/trainnumberquery',
      name: 'TrainNumberQuery',
      component: TrainNumberQuery
    },
    {
      path: '/details',
      name: 'Details',
      component: Details
    },
    {
      path: '/detailsquery',
      name: 'DetailsQuery',
      component: DetailsQuery
    },
    {
      path: '/traintype',
      name: 'TrainType',
      component: TrainType
    },
    {
      path: '/manage',
      name: 'Manage',
      component: Manage
    }
  ]
})
