import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '../views/Home.vue'
import MyPage from '../views/MyPage.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/mypage/:username',
    name: 'MyPage',
    component: MyPage,
    props: true,
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
