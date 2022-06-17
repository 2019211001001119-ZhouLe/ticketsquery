import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Home from '@/components/Home'
import Test from '@/components/Test'
import TrainStation from '@/components/TrainStation'
import Train from '@/components/Train'
import UserQuery from '@/components/UserQuery'

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
      path: '/userquery',
      name: 'UserQuery',
      component: UserQuery,
    }
  ]
})
