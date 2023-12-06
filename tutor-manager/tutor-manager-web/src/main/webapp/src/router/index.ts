import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomePage from '../views/homepage.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomePage
  },
  {
    path: '/dashboard/:userType/:userId',
    name: 'dashboard',
    props: true,
    component: () => import('../views/dashboard.vue')
  },
  {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/login.vue')
  },
  {
    path: '/Register',
    name: 'Register',
    component: () => import('../views/register.vue')
  },
  {
    path: '/ForgetPW',
    name: 'ForgetPW',
    component: () => import('../views/forgetpw.vue')
  },
  {
    path: '/verification',
    name: 'VerificationPage',
    component: () => import('../views/verification.vue'),
    props: (route) => ({ email: route.query.email, userType: route.query.userType })
  },
  {
    path: '/ResetPW',
    name: 'ResetPW',
    component: () => import('../views/resetpw.vue'),
    props: true
  },
  {
    path: '/About',
    name: 'About',
    component: () => import('../views/about.vue')
  },
  {
    path: '/Support',
    name: 'SupportPage',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/support.vue')
  },
  {
    path: '/Account/:userType/:userId',
    name: 'Account',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    props: true,
    component: () => import('../views/account.vue')
  },
  {
    path: '/Message/:userType/:userId',
    name: 'Message',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    props: true,
    component: () => import('../views/Message.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
