import Vue from 'vue'
import Router from 'vue-router'
import HomeView from '@/components/HomeView'
import Test from '@/components/Test'
import TrainStation from '@/components/TrainStation'
import Train from '@/components/Train'
import TrainNumberQuery from '@/components/TrainNumberQuery'
import DetailsQuery from '@/components/DetailsQuery'
import TrainType from '@/components/TrainType'
import Manage from '@/components/Manage'
import LoginView from '@/components/LoginView'

Vue.use(Router)

export default new Router({
  routes: [
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
      path: '/test',
      name: 'Test',
      component: Test
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
      path: '/trainnumberquery',
      name: 'TrainNumberQuery',
      component: TrainNumberQuery
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
