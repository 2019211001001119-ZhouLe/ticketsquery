import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Home from '@/components/Home'
import Test from '@/components/Test'
import TrainStation from '@/components/TrainStation'
import Train from '@/components/Train'
import TrainNumberQuery from '@/components/TrainNumberQuery'
import DetailsQuery from '@/components/DetailsQuery'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
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
      component: TrainNumberQuery,
    },
    {
      path: '/detailsquery',
      name: 'DetailsQuery',
      component: DetailsQuery,
    }
  ]
})
