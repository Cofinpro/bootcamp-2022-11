import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import './assets/style/main.css'

// Vuetify
import 'vuetify/styles'
import vuetify from "@/plugins/vuetify";
import '@mdi/font/css/materialdesignicons.css'



const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(vuetify)

app.mount('#app')
