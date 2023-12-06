import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import ElementPlus from 'element-plus'

import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.mount('#app')
// 全局注册 
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
  }